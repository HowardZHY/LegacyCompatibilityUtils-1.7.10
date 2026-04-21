/*
 * Forge Mod Loader
 * Copyright (c) 2012-2013 cpw.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * https://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 */

package space.libs.asm.remap;

import com.google.common.base.*;
import com.google.common.collect.*;
import com.google.common.io.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.*;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.commons.Remapper;
import org.objectweb.asm.tree.*;
import space.libs.core.CompatLibDebug;

import java.io.*;
import java.net.URL;
import java.util.*;

@SuppressWarnings("UnstableApiUsage")
public abstract class RemapperBase extends Remapper {

    public static final Logger LOGGER = LogManager.getLogger();

    public static boolean DEBUG_REMAPPING = CompatLibDebug.DEBUG_REMAP;

    public RemapperBase(final String file, final int id) {
        this.mappings = Resources.getResource(file);
        this.id = id;
        this.legacy = (id > 9);
        this.renamesMap = Maps.newHashMap();
        this.fieldDescriptions = Maps.newHashMap();
        this.rawFieldMaps = Maps.newHashMap();
        this.rawMethodMaps = Maps.newHashMap();
        this.packagesIn =ImmutableBiMap.builder();
        this.classesIn = ImmutableBiMap.builder();
        this.fieldsIn = ImmutableTable.builder();
        this.methodsIn = ImmutableTable.builder();
        this.negativeFields = Sets.newHashSet();
        this.negativeMethods = Sets.newHashSet();
        try {
            this.setup();
        } catch (Exception e) {
            LOGGER.error("An error occurred loading the custom map data " + file, e);
        }
        this.rawFields = fieldsIn.build();
        this.rawMethods = methodsIn.build();
        this.packagesBiMap = packagesIn.build();
        this.classesBiMap = classesIn.build();
        this.reverseClassMap = classesBiMap.inverse();
        if (this.legacy) {
            this.fieldsMap = Maps.newHashMapWithExpectedSize(this.rawFieldMaps.size());
            this.methodsMap = Maps.newHashMapWithExpectedSize(this.rawMethodMaps.size());
        } else {
            this.fieldsMap = Maps.newHashMapWithExpectedSize(this.rawFields.size());
            this.methodsMap = Maps.newHashMapWithExpectedSize(this.rawMethods.size());
        }
    }

    protected final URL mappings;
    public final int id;
    public final boolean legacy;

    public final ImmutableBiMap<String, String> packagesBiMap;
    public final ImmutableBiMap<String, String> classesBiMap;
    public final ImmutableBiMap<String, String> reverseClassMap;

    protected final ImmutableBiMap.Builder<String, String> packagesIn;
    protected final ImmutableBiMap.Builder<String, String> classesIn;
    protected final ImmutableTable.Builder<String, String, String> fieldsIn;
    protected final ImmutableTable.Builder<String, String, String> methodsIn;

    protected final ImmutableTable<String, String, String> rawFields;
    protected final ImmutableTable<String, String, String> rawMethods;

    protected final Map<String, Map<String, String>> rawFieldMaps;
    protected final Map<String, Map<String, String>> rawMethodMaps;

    protected final Map<String, Map<String, String>> fieldsMap;
    protected final Map<String, Map<String, String>> methodsMap;

    protected final Set<String> negativeFields;
    protected final Set<String> negativeMethods;

    protected final Map<String, String> renamesMap;
    protected final Map<String, Map<String, String>> fieldDescriptions;

    protected void setup() throws IOException {
        Resources.readLines(mappings, Charsets.UTF_8, new MappingLineProcessor());
    }

    protected String getFieldType(String owner, String name) {
        Map<String, String> fields = this.fieldDescriptions.get(owner);
        if (fields != null) {
            return fields.get(name);
        }
        synchronized (fieldDescriptions) {
            byte[] classBytes;
            if (this.legacy) {
                classBytes = this.getBytes(this.map(owner)); // TODO?
            } else {
                classBytes = this.getBytes(owner);
            }
            if (classBytes == null) {
                return null;
            } else {
                ClassReader cr = new ClassReader(classBytes);
                ClassNode classNode = new ClassNode();
                cr.accept(classNode, ClassReader.SKIP_CODE | ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
                Map<String, String> results = Maps.newHashMapWithExpectedSize(classNode.fields.size());
                for (FieldNode fieldNode : classNode.fields) {
                    results.put(fieldNode.name, fieldNode.desc);
                }
                this.fieldDescriptions.put(owner, results);
                return results.get(name);
            }
        }
    }

    public Map<String, String> getFieldMap(String owner) {
        Map<String, String> result = this.fieldsMap.get(owner);
        if (result != null) {
            return result;
        }
        if (!this.negativeFields.contains(owner)) {
            this.loadSuperMaps(owner, false);
            if (!this.fieldsMap.containsKey(owner)) {
                this.negativeFields.add(owner);
            }
        }
        return this.fieldsMap.get(owner);
    }

    public Map<String, String> getMethodMap(String owner) {
        Map<String, String> result = this.methodsMap.get(owner);
        if (result != null) {
            return result;
        }
        if (!this.negativeMethods.contains(owner)) {
            this.loadSuperMaps(owner, false);
            if (!this.methodsMap.containsKey(owner)) {
                this.negativeMethods.add(owner);
            }
        }
        return this.methodsMap.get(owner);
    }

    @Override
    public String mapPackageName(String name) {
        if (this.noPackages()) {
            return name;
        }
        String mapped = this.packagesBiMap.get(name);
        if (mapped != null) {
            return mapped;
        }
        return name;
    }

    @Override
    public String map(String typeName) {
        if (this.noClasses()) {
            return typeName;
        }
        String name = this.classesBiMap.get(typeName);
        if (name != null) {
            return name;
        }
        int inner = typeName.lastIndexOf('$');
        if (inner > -1) {
            return this.map(typeName.substring(0, inner)) + "$" + typeName.substring(inner + 1);
        }
        return typeName;
    }

    public String unmap(String typeName) {
        if (this.noClasses()) {
            return typeName;
        }
        String name = this.reverseClassMap.get(typeName);
        if (name != null) {
            return name;
        }
        int inner = typeName.lastIndexOf('$');
        if (inner > -1) {
            return this.unmap(typeName.substring(0, inner)) + "$" + typeName.substring(inner + 1);
        }
        return typeName;
    }

    @Override
    public String mapFieldName(String owner, String name, String desc) {
        Map<String, String> fields = this.getFieldMap(owner);
        String mapped;
        if (fields != null) {
            mapped = fields.get(name + ':' + desc);
            if (mapped != null) {
                return mapped;
            } else {
                mapped = fields.get(name + ":null");
                if (mapped != null) {
                    if (DEBUG_REMAPPING && (!owner.contains("/") || owner.startsWith("net"))) {
                        DebugRemap("Try map field without desc " + owner + "." + name + " to " + mapped);
                    }
                    return mapped;
                }
            }
        }
        return name;
    }

    @Override
    public String mapMethodName(String owner, String name, String desc) {
        Map<String, String> methods = this.getMethodMap(owner);
        String mapped;
        if (methods != null) {
            mapped = methods.get(name + desc);
            if (mapped != null) {
                return mapped;
            }
        }
        if (this.noRenames()) {
            return name;
        } else {
            mapped = renamesMap.get(name);
            return mapped != null ? mapped : name;
        }
    }

    @SuppressWarnings("Java8MapApi")
    public String getStaticFieldType(String oldType, String oldName, String newType, String newName) {
        String fType = getFieldType(oldType, oldName);
        if (DEBUG_REMAPPING) {
            DebugRemap("Static Field: " + oldType + "+" + oldName + " & " + newType + "+" + newName + " fT: " + fType);
        }
        if (oldType.equals(newType)) {
            return fType;
        }
        Map<String, String> newClassMap = this.fieldDescriptions.get(newType);
        if (newClassMap == null) {
            newClassMap = Maps.newHashMap();
            this.fieldDescriptions.put(newType, newClassMap);
        }
        newClassMap.put(newName, fType);
        return fType;
    }

    /**
     * @implNote Legacy Deobf only
     */
    public String getRealName(String name) {
        return name;
    }

    public String getLegacyName(String name) {
        return name;
    }

    public void loadSuperMaps(String name, boolean chain) {
        byte[] bytes = this.getBytesForSuperMap(name);
        if (bytes != null) {
            ClassReader reader = new ClassReader(bytes);
            this.mergeSuperMaps(name, reader.getSuperName(), reader.getInterfaces(), false);
        }
    }

    public void mergeSuperMaps(String name, String superName, String[] interfaces, boolean visit) {
        if (name.startsWith("java/") || Strings.isNullOrEmpty(superName) || superName.startsWith("net/minecraftforge/event") ) {
            return;
        }
        String[] parents = mergeParents(superName, interfaces, interfaces.length);
        this.mergeSuperMaps(name, parents);
        if (DEBUG_REMAPPING && (!parents[0].startsWith("java"))) {
            LOGGER.info("Computing super maps for " + name + " & " + Arrays.toString(parents) + " visit " + visit);
        }
    }

    public void mergeSuperMaps(String name, String[] parents) {
        for (String parent : parents) {
            if (!this.fieldsMap.containsKey(parent) || !this.methodsMap.containsKey(parent)) {
                this.loadSuperMaps(parent, true);
            }
        }
        Map<String, String> fields = Maps.newHashMap();
        Map<String, String> methods = Maps.newHashMap();
        Map<String, String> value;
        for (String parentThing : parents) {
            value = this.fieldsMap.get(parentThing);
            if (value != null) {
                fields.putAll(value);
            }
            value = this.methodsMap.get(parentThing);
            if (value != null) {
                methods.putAll(value);
            }
        }
        if (this.legacy) {
            value = this.rawFieldMaps.get(name);
            if (value != null) {
                fields.putAll(value);
            }
            value = this.rawMethodMaps.get(name);
            if (value != null) {
                methods.putAll(value);
            }
        } else {
            fields.putAll(this.rawFields.row(name));
            methods.putAll(this.rawMethods.row(name));
        }
        if (DEBUG_REMAPPING && (name.startsWith("net/minecraft/") || !name.contains("/"))) {
            DebugRemap("Field Maps of " + name + ": " + fields);
        }
        this.fieldsMap.put(name, ImmutableMap.copyOf(fields));
        this.methodsMap.put(name, ImmutableMap.copyOf(methods));
    }

    /**
     * @implNote FabricLauncherBase.getLauncher().getTargetClassLoader().getResourceAsStream(name.replace('.', '/') + ".class");
     */
    protected byte[] getBytes(String name) {
        byte[] bytes = null;
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(name.replace('.', '/') + ".class");
            if (is != null) {
                bytes = ByteStreams.toByteArray(is);
            }
        } catch (Throwable ignored) {}
        return bytes;
    }

    protected byte[] getBytesForSuperMap(String name) {
        return this.getBytes(name);
    }

    public boolean noPackages() {
        return this.packagesBiMap == null || this.packagesBiMap.isEmpty();
    }

    public boolean noClasses() {
        return this.classesBiMap == null || this.classesBiMap.isEmpty();
    }

    public boolean noRenames() {
        return this.renamesMap == null || this.renamesMap.isEmpty();
    }

    @SuppressWarnings("unused")
    public boolean isRemappedClass(String className) {
        return !map(className).equals(className);
    }

    public void DebugRemap(String msg) {
        if (this instanceof CustomRemapper) {
            LOGGER.info(msg);
        }
    }

    public static String[] getSignature(String in) {
        int pos = in.lastIndexOf('/');
        return new String[]{in.substring(0, pos), in.substring(pos + 1)};
    }

    public static String[] mergeParents(String superName, String[] interfaces, int l) {
        String[] parents = new String[l + 1];
        parents[0] = superName;
        System.arraycopy(interfaces, 0, parents, 1, l);
        return parents;
    }

    public enum MappingType {

        PACKAGE("PK"), CLASS("CL"), FIELD("FD"), METHOD("MD");

        private static final ImmutableMap<String, MappingType> LOOKUP;

        private final String identifier;

        MappingType(final String identifier) {
            this.identifier = identifier;
        }

        static {
            ImmutableMap.Builder<String, MappingType> builder = ImmutableMap.builder();
            for (MappingType type : MappingType.values()) {
                builder.put(type.identifier + ':', type);
            }
            LOOKUP = builder.build();
        }

        public static MappingType of(final String identifier) {
            return LOOKUP.get(identifier);
        }
    }

    @SuppressWarnings("all")
    public class MappingLineProcessor implements LineProcessor<Void> {

        @Override
        public boolean processLine(String line) {
            if ((line = line.trim()).isEmpty()) {
                return true;
            }
            String[] parts = StringUtils.split(line, ' ');
            if (parts.length < 3) {
                LOGGER.error("Invalid mapping line: " + line);
                return true;
            }
            MappingType type = MappingType.of(parts[0]);
            if (type == null) {
                LOGGER.error("Invalid mapping type: " + line);
                return true;
            }
            String[] source;
            String[] dest;
            switch (type) {
                case PACKAGE:
                    packagesIn.put(parts[1], parts[2]);
                    break;
                case CLASS:
                    classesIn.put(parts[1], parts[2]);
                    break;
                case FIELD:
                    source = getSignature(parts[1]);
                    dest = getSignature(parts[2]);
                    String fieldType = getFieldType(source[0], source[1]);
                    fieldsIn.put(source[0], source[1] + ':' + fieldType, dest[1]);
                    if (fieldType != null) {
                        fieldsIn.put(source[0], source[1] + ":null", dest[1]);
                    }
                    break;
                case METHOD:
                    source = getSignature(parts[1]);
                    dest = getSignature(parts[3]);
                    methodsIn.put(source[0], source[1] + parts[2], dest[1]);
                    break;
                default:
            }
            return true;
        }

        @Override
        public Void getResult() {
            return null;
        }
    }
}
