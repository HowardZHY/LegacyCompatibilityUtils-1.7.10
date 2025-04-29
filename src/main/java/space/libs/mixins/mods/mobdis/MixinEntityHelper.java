package space.libs.mixins.mods.mobdis;

import mobdis.client.entity.EntityBloodFX;
import mobdis.common.MobDismemberment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("all")
@Pseudo
@Mixin(targets = "mobdis.common.core.EntityHelper", remap = false)
public abstract class MixinEntityHelper {

    @Inject(method = "dismember", at = @At("TAIL"))
    private static void dismember(World world, EntityLivingBase living, Entity explo, CallbackInfoReturnable<Boolean> cir) {
        int l;
        if (MobDismemberment.config.getInt("blood") == 1) {
            for (l = 0; l < (explo != null ? MobDismemberment.config.getInt("bloodCount") * 5 : MobDismemberment.config.getInt("bloodCount")); ++l) {
                float var4 = 0.3F;
                double mX = -MathHelper.sin(living.rotationYaw / 180.0F * 3.1416F) * MathHelper.cos(living.rotationPitch / 180.0F * 3.1416F) * var4;
                double mZ = MathHelper.cos(living.rotationYaw / 180.0F * 3.1416F) * MathHelper.cos(living.rotationPitch / 180.0F * 3.1416F) * var4;
                double mY = -MathHelper.sin(living.rotationPitch / 180.0F * 3.1416F) * var4 + 0.1F;
                var4 = 0.02F;
                float var5 = living.getRNG().nextFloat() * 3.1416F * 2.0F;
                var4 *= living.getRNG().nextFloat();
                if (explo != null) {
                    var4 = (float)((double)var4 * 100.0);
                }
                mX += Math.cos(var5) * (double)var4;
                mY += (living.getRNG().nextFloat() - living.getRNG().nextFloat()) * 0.1F;
                mZ += Math.sin(var5) * (double)var4;
                EntityFX blood = new EntityBloodFX(living.worldObj, living.posX, living.posY + 0.5 + living.getRNG().nextDouble() * 0.7, living.posZ, living.motionX + mX, living.motionY + mY, living.motionZ + mZ, living instanceof EntityPlayer);
                Minecraft.getMinecraft().effectRenderer.addEffect(blood);
            }
        }
    }
}
