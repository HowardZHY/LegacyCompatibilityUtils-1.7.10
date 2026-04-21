/*
 * Forge Mod Loader
 * Copyright (c) 2012-2013 cpw.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * https://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 */

package space.libs.asm.remap;

import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import net.minecraft.launchwrapper.*;

import java.io.IOException;

public class DefaultRemapper extends RemapperBase implements IClassNameTransformer {

    public static String DEFAULT_MAPPINGS = "compatlib.srg";

    public static String LEGACY_MAPPINGS = "legacydeobf.srg";

    public final LaunchClassLoader classLoader;

    public final FMLDeobfuscatingRemapper FMLRemapper;

    public DefaultRemapper() {
        this(DEFAULT_MAPPINGS, 1);
    }

    public DefaultRemapper(final String file, final int id) {
        super(file, id);
        this.classLoader = (LaunchClassLoader) this.getClass().getClassLoader();
        this.FMLRemapper = FMLDeobfuscatingRemapper.INSTANCE;
    }

    @Override
    public String remapClassName(String name) {
        return map(name.replace('.', '/')).replace('/', '.');
    }

    @Override
    public String unmapClassName(String name) {
        return unmap(name.replace('.', '/')).replace('/', '.');
    }

    @Override
    protected byte[] getBytes(String name) {
        byte[] bytes = null;
        try {
            bytes = Launch.classLoader.getClassBytes(name.replace('/', '.'));
        } catch (IOException ignored) {}
        return bytes;
    }

    @Override
    protected byte[] getBytesForSuperMap(String name) {
        return getBytes(name);
    }
}
