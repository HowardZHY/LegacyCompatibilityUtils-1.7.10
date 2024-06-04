/*
 * Forge Mod Loader
 * Copyright (c) 2012-2013 cpw.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * https://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 */
package space.libs.mixins.forge;

import cpw.mods.fml.common.registry.LanguageRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;
import java.util.Properties;

@SuppressWarnings("all")
@Mixin(value = LanguageRegistry.class, remap = false)
public abstract class MixinLanguageRegistry {

    @Shadow
    private Map<String, Properties> modLanguageData;

    public void loadLanguageTable(Map field_135032_a, String lang) {
        Properties usPack = this.modLanguageData.get("en_US");
        if (usPack != null)
            field_135032_a.putAll(usPack);
        Properties langPack = this.modLanguageData.get(lang);
        if (langPack == null)
            return;
        field_135032_a.putAll(langPack);
    }
}
