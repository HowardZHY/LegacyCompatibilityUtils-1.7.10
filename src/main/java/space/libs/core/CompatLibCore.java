package space.libs.core;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.MixinBootstrap;

import java.util.ArrayList;
import java.util.Map;

@SuppressWarnings("all")
@IFMLLoadingPlugin.TransformerExclusions({"space.libs.asm", "space.libs.core", "space.libs.util.cursedmixinextensions", "org.joml"})
@IFMLLoadingPlugin.SortingIndex(Integer.MIN_VALUE + 2)
public class CompatLibCore implements IFMLLoadingPlugin {

    public static Logger LOGGER = LogManager.getLogger("CompatLibCore");

    public CompatLibCore() {}

    static {
        MixinBootstrap.init();
        org.spongepowered.asm.mixin.Mixins.addConfiguration("mixins.compatlib.forge.json");
        org.spongepowered.asm.mixin.Mixins.addConfiguration("mixins.compatlib.json");
    }

    @Override
    public String[] getASMTransformerClass() {
        ArrayList<String> transformersList = new ArrayList<>();
        //transformersList.add("space.libs.asm.BlockTransformer");
        transformersList.add("space.libs.asm.EventBusTransformer");
        transformersList.add("space.libs.asm.FMLPlayerTransformer");
        transformersList.add("space.libs.asm.FMLModContainerTransformer");
        transformersList.add("space.libs.asm.ForgeSubscribeTransformer");
        //transformersList.add("space.libs.asm.ItemTransformer");
        transformersList.add("space.libs.asm.MacOSTransformer");
        //transformersList.add("space.libs.asm.RemapTransformer");
        // "space.libs.asm.GameDataTransformer"
        // "space.libs.asm.ReplaceTransformer"
        String[] transformers = new String[transformersList.size()];
        return transformersList.toArray(transformers);
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {}

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

}
