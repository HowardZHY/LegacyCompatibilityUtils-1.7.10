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
import net.minecraft.launchwrapper.*;

import java.io.IOException;

public class DefaultRemapper extends RemapperBase implements IClassNameTransformer {

    public static String DEFAULT_MAPPINGS = "compatlib.srg";

    public static String LEGACY_MAPPINGS = "legacydeobf.srg";

    public final LaunchClassLoader classLoader;

    public DefaultRemapper() {
        this(DEFAULT_MAPPINGS, false);
    }

    public DefaultRemapper(final String file, final boolean deobfuscating) {
        super(file, deobfuscating);
        this.classLoader = (LaunchClassLoader) this.getClass().getClassLoader();
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
        try {
            return Launch.classLoader.getClassBytes(name.replace('/', '.'));
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }
}
