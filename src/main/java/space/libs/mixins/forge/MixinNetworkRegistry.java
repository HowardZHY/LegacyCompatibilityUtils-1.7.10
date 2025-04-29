package space.libs.mixins.forge;

import cpw.mods.fml.common.network.NetworkRegistry;
import org.spongepowered.asm.mixin.*;
import space.libs.util.cursedmixinextensions.annotations.Public;

@Mixin(value = NetworkRegistry.class, remap = false)
public class MixinNetworkRegistry {

    @Shadow
    public static @Final NetworkRegistry INSTANCE;

    @Public
    private static NetworkRegistry instance() {
        return INSTANCE;
    }
}
