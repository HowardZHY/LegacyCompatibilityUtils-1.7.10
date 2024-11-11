/*
 * Forge Mod Loader
 * Copyright (c) 2012-2013 cpw.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * https://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 */

package space.libs.asm;

import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.*;
import com.google.common.collect.ImmutableBiMap.Builder;
import com.google.common.io.CharSource;
import com.google.common.io.Resources;
import cpw.mods.fml.common.patcher.ClassPatchManager;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.commons.Remapper;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import space.libs.core.CompatLibCore;
import space.libs.core.CompatLibDebug;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.io.Resources.getResource;

public class CustomRemapRemapper extends Remapper {

    private BiMap<String, String> classNameBiMap;

    private Map<String,Map<String,String>> rawFieldMaps;

    private Map<String,Map<String,String>> rawMethodMaps;

    private Map<String,Map<String,String>> fieldNameMaps;

    private Map<String,Map<String,String>> methodNameMaps;

    private LaunchClassLoader classLoader;

    public static String DEFAULT_MAPPINGS = "compatlib.srg";

    public CustomRemapRemapper() {
        classNameBiMap = ImmutableBiMap.of();
        this.setup((LaunchClassLoader) this.getClass().getClassLoader(), DEFAULT_MAPPINGS);
    }

    public void setup(LaunchClassLoader classLoader, String deobfFileName) {
        this.classLoader = classLoader;
        try {
            URL mappings = getResource(deobfFileName);
            CharSource srgSource = Resources.asCharSource(mappings, Charsets.UTF_8);
            List<String> srgList = srgSource.readLines();
            rawMethodMaps = Maps.newHashMap();
            rawFieldMaps = Maps.newHashMap();
            Builder<String, String> builder = ImmutableBiMap.builder();
            Splitter splitter = Splitter.on(CharMatcher.anyOf(": ")).omitEmptyStrings().trimResults();
            for (String line : srgList)
            {
                String[] parts = Iterables.toArray(splitter.split(line),String.class);
                String typ = parts[0];
                if ("CL".equals(typ))
                {
                    parseClass(builder, parts);
                }
                else if ("MD".equals(typ))
                {
                    parseMethod(parts);
                }
                else if ("FD".equals(typ))
                {
                    parseField(parts);
                }
            }
            classNameBiMap = builder.build();
        } catch (IOException ioe) {
            CompatLibCore.LOGGER.error("An error occurred loading the deobfuscation map data" + ioe);
        }
        methodNameMaps = Maps.newHashMapWithExpectedSize(rawMethodMaps.size());
        fieldNameMaps = Maps.newHashMapWithExpectedSize(rawFieldMaps.size());
    }

    public boolean isRemappedClass(String className)
    {
        return !map(className).equals(className);
    }

    private void parseField(String[] parts)
    {
        String oldSrg = parts[1];
        int lastOld = oldSrg.lastIndexOf('/');
        String cl = oldSrg.substring(0,lastOld);
        String oldName = oldSrg.substring(lastOld+1);
        String newSrg = parts[2];
        int lastNew = newSrg.lastIndexOf('/');
        String newName = newSrg.substring(lastNew+1);
        if (!rawFieldMaps.containsKey(cl))
        {
            rawFieldMaps.put(cl, Maps.newHashMap());
        }
        rawFieldMaps.get(cl).put(oldName + ":" + getFieldType(cl, oldName), newName);
        rawFieldMaps.get(cl).put(oldName + ":null", newName);
    }

    private Map<String,Map<String,String>> fieldDescriptions = Maps.newHashMap();

    private Set<String> negativeCacheMethods = Sets.newHashSet();

    private Set<String> negativeCacheFields = Sets.newHashSet();

    private String getFieldType(String owner, String name) {
        if (fieldDescriptions.containsKey(owner)) {
            return fieldDescriptions.get(owner).get(name);
        }
        synchronized (fieldDescriptions) {
            try {
                byte[] classBytes = ClassPatchManager.INSTANCE.getPatchedResource(owner, map(owner).replace('/', '.'), classLoader);
                if (classBytes == null) {
                    return null;
                }
                ClassReader cr = new ClassReader(classBytes);
                ClassNode classNode = new ClassNode();
                cr.accept(classNode, ClassReader.SKIP_CODE | ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
                Map<String,String> resMap = Maps.newHashMap();
                for (FieldNode fieldNode : classNode.fields) {
                    resMap.put(fieldNode.name, fieldNode.desc);
                }
                fieldDescriptions.put(owner, resMap);
                return resMap.get(name);
            } catch (IOException e) {
                CompatLibCore.LOGGER.error(e + " A critical exception occured reading a class file " + owner);
            }
            return null;
        }
    }

    private void parseClass(Builder<String, String> builder, String[] parts) {
        builder.put(parts[1],parts[2]);
    }

    private void parseMethod(String[] parts) {
        String oldSrg = parts[1];
        int lastOld = oldSrg.lastIndexOf('/');
        String cl = oldSrg.substring(0,lastOld);
        String oldName = oldSrg.substring(lastOld+1);
        String sig = parts[2];
        String newSrg = parts[3];
        int lastNew = newSrg.lastIndexOf('/');
        String newName = newSrg.substring(lastNew+1);
        if (!rawMethodMaps.containsKey(cl)) {
            rawMethodMaps.put(cl, Maps.<String,String>newHashMap());
        }
        rawMethodMaps.get(cl).put(oldName+sig, newName);
    }

    @Override
    public String mapFieldName(String owner, String name, String desc) {
        if (classNameBiMap == null || classNameBiMap.isEmpty()) {
            return name;
        }
        Map<String, String> fieldMap = getFieldMap(owner);
        return fieldMap!=null && fieldMap.containsKey(name+":"+desc) ? fieldMap.get(name+":"+desc) : name;
    }

    @Override
    public String map(String typeName) {
        if (classNameBiMap == null || classNameBiMap.isEmpty()) {
            return typeName;
        }
        if (classNameBiMap.containsKey(typeName)) {
            return classNameBiMap.get(typeName);
        }
        int dollarIdx = typeName.lastIndexOf('$');
        if (dollarIdx > -1)
        {
            return map(typeName.substring(0, dollarIdx)) + "$" + typeName.substring(dollarIdx + 1);
        }
        return typeName;
    }

    public String unmap(String typeName) {
        if (classNameBiMap == null || classNameBiMap.isEmpty()) {
            return typeName;
        }

        if (classNameBiMap.containsValue(typeName)) {
            return classNameBiMap.inverse().get(typeName);
        }
        int dollarIdx = typeName.lastIndexOf('$');
        if (dollarIdx > -1) {
            return unmap(typeName.substring(0, dollarIdx)) + "$" + typeName.substring(dollarIdx + 1);
        }
        return typeName;
    }

    @Override
    public String mapMethodName(String owner, String name, String desc) {
        if (classNameBiMap==null || classNameBiMap.isEmpty()) {
            return name;
        }
        Map<String, String> methodMap = getMethodMap(owner);
        String methodDescriptor = name+desc;
        return methodMap!=null && methodMap.containsKey(methodDescriptor) ? methodMap.get(methodDescriptor) : name;
    }

    private Map<String,String> getFieldMap(String className) {
        if (!fieldNameMaps.containsKey(className) && !negativeCacheFields.contains(className)) {
            findAndMergeSuperMaps(className);
            if (!fieldNameMaps.containsKey(className)) {
                negativeCacheFields.add(className);
            }

            if (CompatLibDebug.DEBUG) {
                CompatLibCore.LOGGER.info("Field map for " + className + " : " + fieldNameMaps.get(className));
            }
        }
        return fieldNameMaps.get(className);
    }

    private Map<String,String> getMethodMap(String className) {
        if (!methodNameMaps.containsKey(className) && !negativeCacheMethods.contains(className)) {
            findAndMergeSuperMaps(className);
            if (!methodNameMaps.containsKey(className)) {
                negativeCacheMethods.add(className);
            }
            if (CompatLibDebug.DEBUG) {
                CompatLibCore.LOGGER.info("Method map for " + className + " : " + methodNameMaps.get(className));
            }
        }
        return methodNameMaps.get(className);
    }

    private void findAndMergeSuperMaps(String name) {
        try {
            String superName = null;
            String[] interfaces = new String[0];
            byte[] classBytes = ClassPatchManager.INSTANCE.getPatchedResource(name, map(name), classLoader);
            if (classBytes != null) {
                ClassReader cr = new ClassReader(classBytes);
                superName = cr.getSuperName();
                interfaces = cr.getInterfaces();
            }
            mergeSuperMaps(name, superName, interfaces);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mergeSuperMaps(String name, String superName, String[] interfaces) {
//      System.out.printf("Computing super maps for %s: %s %s\n", name, superName, Arrays.asList(interfaces));
        if (classNameBiMap == null || classNameBiMap.isEmpty()) {
            return;
        }
        // Skip Object
        if (Strings.isNullOrEmpty(superName)) {
            return;
        }
        List<String> allParents = ImmutableList.<String>builder().add(superName).addAll(Arrays.asList(interfaces)).build();
        // generate maps for all parent objects
        for (String parentThing : allParents) {
            if (!methodNameMaps.containsKey(parentThing)) {
                findAndMergeSuperMaps(parentThing);
            }
        }
        Map<String, String> methodMap = Maps.newHashMap();
        Map<String, String> fieldMap = Maps.newHashMap();
        for (String parentThing : allParents) {
            if (methodNameMaps.containsKey(parentThing)) {
                methodMap.putAll(methodNameMaps.get(parentThing));
            }
            if (fieldNameMaps.containsKey(parentThing)) {
                fieldMap.putAll(fieldNameMaps.get(parentThing));
            }
        }
        if (rawMethodMaps.containsKey(name)) {
            methodMap.putAll(rawMethodMaps.get(name));
        }
        if (rawFieldMaps.containsKey(name)) {
            fieldMap.putAll(rawFieldMaps.get(name));
        }
        methodNameMaps.put(name, ImmutableMap.copyOf(methodMap));
        fieldNameMaps.put(name, ImmutableMap.copyOf(fieldMap));
//      System.out.printf("Maps: %s %s\n", name, methodMap);
    }

    public Set<String> getObfedClasses() {
        return ImmutableSet.copyOf(classNameBiMap.keySet());
    }

    public String getStaticFieldType(String oldType, String oldName, String newType, String newName) {
        String fType = getFieldType(oldType, oldName);
        if (oldType.equals(newType)) {
            return fType;
        }
        Map<String,String> newClassMap = fieldDescriptions.get(newType);
        if (newClassMap == null) {
            newClassMap = Maps.newHashMap();
            fieldDescriptions.put(newType, newClassMap);
        }
        newClassMap.put(newName, fType);
        return fType;
    }
}
