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
import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import cpw.mods.fml.common.patcher.ClassPatchManager;
import org.objectweb.asm.ClassReader;

import java.io.IOException;
import java.util.*;

@SuppressWarnings("UnstableApiUsage")
public class CustomRemapper extends DefaultRemapper {

    public static boolean DEBUG_CUSTOM_REMAPPING = false;

    public final FMLDeobfuscatingRemapper FMLRemapper;

    public CustomRemapper(String name) {
        super(name, true);
        FMLRemapper = FMLDeobfuscatingRemapper.INSTANCE;
    }

    @Override
    protected void setup() throws IOException {
        CharSource srgSource = Resources.asCharSource(this.mappings, Charsets.UTF_8);
        List<String> srgList = srgSource.readLines();
        Splitter splitter = Splitter.on(CharMatcher.anyOf(": ")).omitEmptyStrings().trimResults();
        for (String line : srgList) {
            String[] parts = Iterables.toArray(splitter.split(line),String.class);
            String typ = parts[0];
            if ("CL".equals(typ)) {
                this.classesIn.put(parts[1],parts[2]);
            }
            else if ("MD".equals(typ)) {
                parseMethod(parts);
            }
            else if ("FD".equals(typ)) {
                parseField(parts);
            }
        }
    }

    private void parseField(String[] parts) {
        String oldSrg = parts[1];
        int lastOld = oldSrg.lastIndexOf('/');
        String cl = oldSrg.substring(0,lastOld);
        String oldName = oldSrg.substring(lastOld+1);
        String newSrg = parts[2];
        int lastNew = newSrg.lastIndexOf('/');
        String newName = newSrg.substring(lastNew+1);
        if (!rawFieldMaps.containsKey(cl)) {
            rawFieldMaps.put(cl, Maps.newHashMap());
        }
        String typed = getFieldType(cl, oldName);
        if (typed != null) {
            rawFieldMaps.get(cl).put(oldName + ":" + typed, newName);
        }
        rawFieldMaps.get(cl).put(oldName + ":null", newName);
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
            rawMethodMaps.put(cl, Maps.newHashMap());
        }
        rawMethodMaps.get(cl).put(oldName+sig, newName);
    }

    @Override
    public String getRealName(String name) {
        if (Strings.isNullOrEmpty(name)) {
            return name;
        }
        if (name.contains("/")) {
            if (name.startsWith("net/minecraft/")) {
                return FMLRemapper.unmap(name);
            } else {
                return name; // Not Mapped
            }
        }
        String mappedName = this.map(name);
        String realName = FMLRemapper.unmap(mappedName);
        if (DEBUG_CUSTOM_REMAPPING && (!name.equals(realName))) {
            LOGGER.info("Get " + name + "'s unmapped name " + realName + " from " + mappedName);
        }
        return realName;
    }

    @Override
    public String getLegacyName(String name) {
        if (Strings.isNullOrEmpty(name)) {
            return name;
        }
        if (name.contains("/")) {
            if (name.startsWith("net/minecraft/")) {
                return this.unmap(name);
            } else {
                return name; // Not Mapped
            }
        } else {
            String mapped = FMLRemapper.map(name);
            return this.unmap(mapped);
        }
    }

    @Override
    public void loadSuperMaps(String name, boolean chain) {
        if (Strings.isNullOrEmpty(name) || name.startsWith("java")) {
            return;
        }
        byte[] bytes = this.getBytesForSuperMap(this.getRealName(name));
        if (bytes != null) {
            ClassReader cr = new ClassReader(bytes);
            String superName = cr.getSuperName();
            String[] interfaces = cr.getInterfaces();
            if (DEBUG_CUSTOM_REMAPPING && !name.startsWith("java")) {
                LOGGER.info("Try finding super map for " + name + " to " + superName + " chain: " + chain);
            }
            if (chain) {
                String[] legacyInterfaces = new String[interfaces.length];
                for (int i = 0; i < interfaces.length; i++) {
                    legacyInterfaces[i] = this.getLegacyName(interfaces[i]);
                }
                this.mergeSuperMaps(name, this.getLegacyName(superName), legacyInterfaces);
            } else {
                this.mergeSuperMaps(name, superName, interfaces);
            }
        }
    }

    @Override
    protected byte[] getBytesForSuperMap(String name) {
        byte[] bytes = null;
        try {
            bytes = ClassPatchManager.INSTANCE.getPatchedResource(name, this.map(name), this.classLoader);
        } catch (Throwable ignored) {}
        if (bytes == null) {
            bytes = super.getBytesForSuperMap(name);
        }
        return bytes;
    }

    @SuppressWarnings("unused")
    public Set<String> getObfedClasses() {
        return ImmutableSet.copyOf(this.classesBiMap.keySet());
    }
}
