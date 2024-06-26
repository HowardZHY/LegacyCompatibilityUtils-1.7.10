package space.libs.core;

import com.gtnewhorizon.gtnhmixins.IEarlyMixinLoader;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.MixinBootstrap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("all")
@IFMLLoadingPlugin.TransformerExclusions({"space.libs.asm", "space.libs.core", "space.libs.util.cursedmixinextensions", "org.joml"})
@IFMLLoadingPlugin.SortingIndex(Integer.MIN_VALUE + 5)
public class CompatLibCore implements IFMLLoadingPlugin, IEarlyMixinLoader {

    public static Logger LOGGER = LogManager.getLogger("CompatLibCore");

    public CompatLibCore() {}

    static {
        MixinBootstrap.init();
        org.spongepowered.asm.mixin.Mixins.addConfiguration("mixins.compatlib.json");
    }

    @Override
    public String getMixinConfig() {
        return "mixins.compatlib.forge.json";
    }

    @Override
    public List<String> getMixins(Set<String> loadedCoreMods) {
        List<String> mixins = new ArrayList<>();
        mixins.add("MixinFMLModContainer");
        mixins.add("MixinLanguageRegistry");
        return mixins;
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[] {
            "space.libs.asm.MacOSTransformer"
            // "space.libs.asm.GameDataTransformer"
            // "space.libs.asm.RemapTransformer"
            // "space.libs.asm.ReplaceTransformer"
        };
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
