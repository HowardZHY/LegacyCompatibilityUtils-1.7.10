package space.libs.mixins;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.world.EnumDifficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@SuppressWarnings("unused")
@Mixin(MinecraftServer.class)
public abstract class MixinMinecraftServer {

    @Shadow
    private ServerConfigurationManager serverConfigManager;

    @Shadow
    public abstract EnumDifficulty func_147135_j();

    @Shadow
    public void func_147139_a(EnumDifficulty difficulty) {}

    /** setConfigurationManager */
    public void func_71210_a(ServerConfigurationManager manager) {
        this.serverConfigManager = manager;
    }

    /** setDifficultyForAllWorlds */
    public void func_71226_c(int difficulty) {
        this.func_147139_a(EnumDifficulty.getDifficultyEnum(difficulty));
    }

    /** getDifficulty */
    public int func_71232_g() {
        return this.func_147135_j().getDifficultyId();
    }
}
