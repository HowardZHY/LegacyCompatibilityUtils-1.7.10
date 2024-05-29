package space.libs.mixins;

import net.minecraft.server.management.PlayerManager;
import net.minecraft.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.util.cursedmixinextensions.annotations.NewConstructor;
import space.libs.util.cursedmixinextensions.annotations.ShadowConstructor;

@SuppressWarnings("unused")
@Mixin(PlayerManager.class)
public abstract class MixinPlayerManager {

    @Shadow
    private int playerViewRadius;

    @ShadowConstructor
    public void PlayerManager(WorldServer p_i1176_1_) {}

    @NewConstructor
    public void PlayerManager(WorldServer p_i1176_1_, int viewRadius) {
        this.PlayerManager(p_i1176_1_);
        this.playerViewRadius = viewRadius;
    }

}
