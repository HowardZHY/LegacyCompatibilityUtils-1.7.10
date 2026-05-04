package space.libs.core;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.mixin.Mixins;
import space.libs.util.ModDetector;
import space.libs.util.forge.ModLoadingUtils;

import java.util.*;

@IFMLLoadingPlugin.TransformerExclusions({"space.libs.asm", "space.libs.core", "space.libs.util.cursedmixinextensions", "org.joml"})
@IFMLLoadingPlugin.SortingIndex(Integer.MIN_VALUE + 2)
public class CompatLibCore implements IFMLLoadingPlugin, ICoreUtils {

    public static boolean BUKKIT = false;

    public CompatLibCore() {
        classLoader.registerTransformer("space.libs.asm.EarliestTransformer");
        if (ModDetector.hasClassBytes("org.bukkit.craftbukkit.util.Versioning")) {
            LOGGER.info("Running in Hybrid Server...");
            BUKKIT = true;
        }
    }

    static {
        LOGGER.info("Initializing CompatLib Core Class...");
        CompatLibLateCore.init();
    }

    @Override
    public String[] getASMTransformerClass() {
        Mixins.addConfiguration("mixins.compatlib.forge.json");
        Mixins.addConfiguration("mixins.compatlib.json");
        ModLoadingUtils.init();
        ArrayList<String> transformersList = new ArrayList<>();
        transformersList.add("space.libs.asm.ClassTransformers");
        transformersList.add("space.libs.asm.DefaultCompatTransformer");
        // "space.libs.asm.GameDataTransformer"
        String[] transformers = new String[transformersList.size()];
        return transformersList.toArray(transformers);
    }

    @Override
    public String getModContainerClass() {
        return "space.libs.core.CompatLibCoreContainer";
    }

    @Override
    public String getSetupClass() {
        return "space.libs.core.CompatLibSetupHook";
    }

    @Override
    public void injectData(Map<String, Object> data) {}

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

}
