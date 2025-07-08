package space.libs.mixins.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityFishHook.class)
public abstract class MixinEntityFishHook extends Entity {

    public MixinEntityFishHook(World worldIn) {
        super(worldIn);
    }

    @Shadow
    public EntityPlayer angler;

    @Inject(
        method = "onUpdate",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;onUpdate()V", shift = At.Shift.AFTER),
        cancellable = true
    )
    public void onUpdate(CallbackInfo ci) {
        if (this.angler == null) {
            this.setDead();
            ci.cancel();
        }
    }
}
