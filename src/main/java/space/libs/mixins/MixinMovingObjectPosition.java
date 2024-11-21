package space.libs.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MovingObjectPosition.class)
public abstract class MixinMovingObjectPosition {

    /** typeOfHit */
    public EnumMovingObjectType field_72313_a;

    @Inject(method = "<init>(IIIILnet/minecraft/util/Vec3;)V", at = @At("TAIL"))
    public void init(int p_i2303_1_, int p_i2303_2_, int p_i2303_3_, int p_i2303_4_, Vec3 p_i2303_5_, CallbackInfo ci) {
        this.field_72313_a = EnumMovingObjectType.TILE;
    }

    @Inject(method = "<init>(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/Vec3;)V", at = @At("TAIL"))
    public void init(Entity e, Vec3 v, CallbackInfo ci) {
        this.field_72313_a = EnumMovingObjectType.ENTITY;
    }
}
