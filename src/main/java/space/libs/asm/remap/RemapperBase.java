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

@SuppressWarnings({"UnstableApiUsage", "CommentedOutCode"})
public abstract class RemapperBase extends Remapper {

    public static final Logger LOGGER = LogManager.getLogger();

    public static boolean DEBUG_REMAPPING = CompatLibDebug.DEBUG_REMAP;

    public RemapperBase(final String file, final boolean deobfuscating) {
        this.mappings = Resources.getResource(file);
        this.legacy = deobfuscating;
        this.fieldDescriptions = Maps.newHashMap();
        this.rawFieldMaps = Maps.newHashMap();
        this.rawMethodMaps = Maps.newHashMap();
        this.classesIn = ImmutableBiMap.builder();
        this.fieldsIn = ImmutableTable.builder();
        this.methodsIn = ImmutableTable.builder();
        this.negativeFields = Sets.newHashSet();
        this.negativeMethods = Sets.newHashSet();
        this.setup();
        this.rawFields = fieldsIn.build();
        this.rawMethods = methodsIn.build();
        this.classesBiMap = classesIn.build();
        this.reverseClassMap = classesBiMap.inverse();
        if (deobfuscating) {
            this.fieldsMap = Maps.newHashMapWithExpectedSize(this.rawFieldMaps.size());
            this.methodsMap = Maps.newHashMapWithExpectedSize(this.rawMethodMaps.size());
        } else {
            this.fieldsMap = Maps.newHashMapWithExpectedSize(this.rawFields.size());
            this.methodsMap = Maps.newHashMapWithExpectedSize(this.rawMethods.size());
        }
    }

    protected final URL mappings;
    public final Boolean legacy;

    public final ImmutableBiMap<String, String> classesBiMap;
    public final ImmutableBiMap<String, String> reverseClassMap;

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

    protected final Map<String, Map<String, String>> fieldDescriptions;

    protected void setup() {
        try {
            Resources.readLines(mappings, Charsets.UTF_8, new MappingLineProcessor());
        } catch (IOException e) {
            LOGGER.error(e);
        }
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
            loadSuperMaps(owner);
            if (!this.fieldsMap.containsKey(owner)) {
                this.negativeFields.add(owner);
            }
            /*if (DEBUG_REMAPPING && !owner.startsWith("java")) {
                LOGGER.info("Field map for " + owner + " : " + fieldsMap.get(owner));
            }*/
        }
        return this.fieldsMap.get(owner);
    }

    public Map<String, String> getMethodMap(String owner) {
        Map<String, String> result = this.methodsMap.get(owner);
        if (result != null) {
            return result;
        }
        if (!this.negativeMethods.contains(owner)) {
            loadSuperMaps(owner);
            if (!this.methodsMap.containsKey(owner)) {
                this.negativeMethods.add(owner);
            }
            /*if (DEBUG_REMAPPING && !owner.startsWith("java")) {
                LOGGER.info("Method map for " + owner + " : " + methodsMap.get(owner));
            }*/
        }
        return this.methodsMap.get(owner);
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
        if (this.noClasses()) {
            return name;
        }
        Map<String, String> fields = getFieldMap(owner);
        if (fields != null) {
            String mapped = fields.get(name + ':' + desc);
            if (mapped != null) {
                return mapped;
            } else {
                mapped = fields.get(name + ":null");
                if (mapped != null) {
                    if (DEBUG_REMAPPING && (!owner.contains("/") || owner.startsWith("net"))) {
                        LOGGER.info("Try map field without desc " + owner + "." + name + " to " + mapped);
                    }
                    return mapped;
                }
            }
        }
        return name;
    }

    @Override
    public String mapMethodName(String owner, String name, String desc) {
        if (this.noClasses()) {
            return name;
        }
        Map<String, String> methods = getMethodMap(owner);
        if (methods != null) {
            String mapped = methods.get(name + desc);
            if (mapped != null) {
                return mapped;
            }
        }
        return name;
    }

    @SuppressWarnings("Java8MapApi")
    public String getStaticFieldType(String oldType, String oldName, String newType, String newName) {
        String fType = getFieldType(oldType, oldName);
        if (DEBUG_REMAPPING) {
            LOGGER.info("Static Field: " + oldType + "+" + oldName + " & " + newType + "+" + newName + " fT: " + fType);
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

    public void loadSuperMaps(String name) {
        byte[] bytes = this.getBytes(name);
        if (bytes != null) {
            ClassReader reader = new ClassReader(bytes);
            this.mergeSuperMaps(name, reader.getSuperName(), reader.getInterfaces());
        }
    }

    public void mergeSuperMaps(String name, String superName, String[] interfaces) {
        if (Strings.isNullOrEmpty(superName)) {
            return;
        }
        if (DEBUG_REMAPPING && (!superName.startsWith("java") || !name.startsWith("java"))) {
            LOGGER.info("Computing super maps for " + name + " & " + superName);
            LOGGER.info("Interfaces: " + Arrays.toString(interfaces));
        }
        String[] parents = new String[interfaces.length + 1];
        parents[0] = superName;
        System.arraycopy(interfaces, 0, parents, 1, interfaces.length);
        for (String parent : parents) {
            if ((this.legacy && !this.methodsMap.containsKey(parent)) ||
                (!this.legacy && !this.fieldsMap.containsKey(parent))) {
                loadSuperMaps(parent);
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
            if (rawFieldMaps.containsKey(name)) {
                fields.putAll(rawFieldMaps.get(name));
            }
            if (rawMethodMaps.containsKey(name)) {
                methods.putAll(rawMethodMaps.get(name));
            }
        } else {
            fields.putAll(this.rawFields.row(name));
            methods.putAll(this.rawMethods.row(name));
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

    public boolean noClasses() {
        return this.classesBiMap == null || this.classesBiMap.isEmpty();
    }

    @SuppressWarnings("unused")
    public boolean isRemappedClass(String className) {
        return !map(className).equals(className);
    }

    public static String[] getSignature(String in) {
        int pos = in.lastIndexOf('/');
        return new String[]{in.substring(0, pos), in.substring(pos + 1)};
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
