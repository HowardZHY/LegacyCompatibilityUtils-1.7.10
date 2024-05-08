package space.libs.mixins.forge;

import com.google.common.base.Strings;
import cpw.mods.fml.common.FMLModContainer;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.MetadataCollection;
import cpw.mods.fml.common.versioning.VersionParser;
import cpw.mods.fml.common.versioning.VersionRange;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@SuppressWarnings("all")
@Mixin(FMLModContainer.class)
public class MixinFMLModContainer {

    @Shadow(remap = false)
    private Map<String, Object> descriptor;

    @Shadow(remap = false)
    private VersionRange minecraftAccepted;

    @Inject(method = "bindMetadata", at = @At("TAIL"), remap = false)
    public void bindMetadata(MetadataCollection collection, CallbackInfo ci) {
        String mcVersionString = (String)descriptor.get("acceptedMinecraftVersions");
        if ((mcVersionString == null) || (mcVersionString != "[1.7.10]")) {
            mcVersionString = "[1.7.2,)";
        }
        if (!Strings.isNullOrEmpty(mcVersionString)) {
            minecraftAccepted = VersionParser.parseRange(mcVersionString);
        } else {
            minecraftAccepted = Loader.instance().getMinecraftModContainer().getStaticVersionRange();
        }
    }
}

