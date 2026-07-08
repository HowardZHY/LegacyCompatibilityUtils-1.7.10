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
import com.google.common.collect.Iterables;
import com.google.common.io.*;
import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import net.minecraft.launchwrapper.*;
import space.libs.core.ICoreUtils;

import java.io.IOException;
import java.util.List;

public class DefaultRemapper extends RemapperBase implements IClassNameTransformer {

    public static final String DEFAULT_MAPPINGS = "compatlib.srg";

    public static String FIELD_RENAMES = "compatlib_fields.csv";

    public static String METHOD_RENAMES = "compatlib_methods.csv";

    public static String LEGACY_MAPPINGS = "legacydeobf.srg";

    public static final DefaultRemapper INSTANCE = new DefaultRemapper();

    public final LaunchClassLoader classLoader;

    public final FMLDeobfuscatingRemapper FMLRemapper;

    private DefaultRemapper() {
        this(DEFAULT_MAPPINGS, 1);
        this.setupDefault(FIELD_RENAMES, METHOD_RENAMES);
    }

    public DefaultRemapper(final String file, final int id) {
        super(file, id);
        this.classLoader = (LaunchClassLoader) this.getClass().getClassLoader();
        this.FMLRemapper = FMLDeobfuscatingRemapper.INSTANCE;
    }

    protected void setupDefault(final String fields, final String methods) {
        ICoreUtils.EXECUTOR.execute(() -> this.setupRename(fields, true));
        ICoreUtils.EXECUTOR.execute(() -> this.setupRename(methods, false));
    }

    @SuppressWarnings("UnstableApiUsage")
    protected void setupRename(final String file, final boolean flag) {
        try {
            CharSource srgSource = Resources.asCharSource(Resources.getResource(file), Charsets.UTF_8);
            List<String> csvList = srgSource.readLines();
            Splitter splitter = Splitter.on(CharMatcher.anyOf(",")).omitEmptyStrings().trimResults();
            for (String line : csvList) {
                line = line.trim();
                if (line.length() < 1 || line.startsWith("#")) continue;
                String[] parts = Iterables.toArray(splitter.split(line), String.class);
                String from = parts[0];
                String to = parts[1];
                if (flag) {
                    this.fieldRenamesMap.put(from, to);
                } else {
                    this.methodRenamesMap.put(from, to);
                }
            }
        } catch (Exception e) {
            LOGGER.error("An error occurred loading the custom csv data ", e);
        }
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
}
