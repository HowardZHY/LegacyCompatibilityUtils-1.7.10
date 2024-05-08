package space.libs.mixins;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@SuppressWarnings("unused")
@Mixin(MinecraftServer.class)
public abstract class MixinMinecraftServer {

    @Shadow
    private ServerConfigurationManager serverConfigManager;

    /** setConfigurationManager */
    public void func_71210_a(ServerConfigurationManager manager) {
        this.serverConfigManager = manager;
    }

}
