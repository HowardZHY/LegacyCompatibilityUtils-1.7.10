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
import cpw.mods.fml.common.patcher.ClassPatchManager;
import org.objectweb.asm.ClassReader;

public class CustomRemapper extends DefaultRemapper {

    public CustomRemapper(String name, int id) {
        super(name, id);
    }

    @Override
    public String getRealName(String name) {
        if (Strings.isNullOrEmpty(name)) {
            return name;
        }
        if (name.contains("/")) {
            if (isMCPackage(name)) {
                return this.FMLRemapper.unmap(name);
            } else {
                return name; // Not Mapped
            }
        }
        String mappedName = this.map(name);
        String realName = this.FMLRemapper.unmap(mappedName);
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
            if (isMCPackage(name)) {
                return this.unmap(name);
            } else {
                return name; // Not Mapped
            }
        } else {
            String mapped = this.FMLRemapper.map(name);
            return this.unmap(mapped);
        }
    }

    @Override
    public void loadSuperMaps(String name, boolean chain) {
        if (Strings.isNullOrEmpty(name) || name.startsWith("java")) {
            return;
        }
        String realName = this.getRealName(name);
        byte[] bytes = this.getBytesForSuperMap(realName);
        if (bytes != null) {
            ClassReader cr = new ClassReader(bytes);
            String superName = cr.getSuperName();
            String[] interfaces = cr.getInterfaces();
            if (DEBUG_CUSTOM_REMAPPING && !name.startsWith("java")) {
                LOGGER.info("Try loading super map for " + name + " to " + superName + " chain: " + chain);
            }
            this.mergeSuperMaps(name, superName, interfaces, (chain || isMCPackage(superName) || isMCPackage(name) || isRemappedClass(name)), false);
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
}
