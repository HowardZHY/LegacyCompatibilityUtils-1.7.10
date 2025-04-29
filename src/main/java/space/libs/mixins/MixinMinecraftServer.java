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
    public abstract EnumDifficulty getDifficulty();

    @Shadow
    public void setDifficultyForAllWorlds(EnumDifficulty difficulty) {}

    /** setConfigurationManager */
    public void func_71210_a(ServerConfigurationManager manager) {
        this.serverConfigManager = manager;
    }

    /** setDifficultyForAllWorlds */
    public void func_71226_c(int difficulty) {
        this.setDifficultyForAllWorlds(EnumDifficulty.getDifficultyEnum(difficulty));
    }

    /** getDifficulty */
    public int func_71232_g() {
        return this.getDifficulty().getDifficultyId();
    }
}
