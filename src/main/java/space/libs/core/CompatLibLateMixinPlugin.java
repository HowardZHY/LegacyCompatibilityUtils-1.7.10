package space.libs.core;

import com.gtnewhorizon.gtnhmixins.ILateMixinLoader;
import com.gtnewhorizon.gtnhmixins.LateMixin;
import space.libs.CompatLib;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
@LateMixin
public class CompatLibLateMixinPlugin implements ILateMixinLoader {
    @Override
    public String getMixinConfig() {
        return "mixins.compatlib.mods.json";
    }

    @Override
    public List<String> getMixins(Set<String> loadedMods) {
        List<String> mixins = new ArrayList<>();
        if (loadedMods.contains("eternisles")) {
            CompatLib.LOGGER.info("Found Eternal Isles. Injecting Fixes...");
            mixins.add("aoa.MixinArmorEffects");
            mixins.add("aoa.MixinClientProxy");
        } else {
            CompatLib.LOGGER.info("Eternal Isles was not found.");
        }
        if (loadedMods.contains("nevermine")) {
            CompatLib.LOGGER.info("Found AoA2. Disabling Update Checker...");
            mixins.add("aoa.MixinUpdateChecker");
        } else {
            CompatLib.LOGGER.info("AoA2 was not found.");
        }
        if (loadedMods.contains("DamageIndicatorsMod")) {
            CompatLib.LOGGER.info("Found Damage Indicators. Disabling Update Checker...");
            mixins.add("di.MixinDIClientProxy");
        } else {
            CompatLib.LOGGER.info("Damage Indicators was not found.");
        }
        return mixins;
    }

}
