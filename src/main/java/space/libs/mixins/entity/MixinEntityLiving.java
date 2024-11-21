package space.libs.mixins.entity;

import net.minecraft.entity.EntityLiving;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityLiving.class)
public abstract class MixinEntityLiving extends MixinEntityLivingBase {

    @Shadow
    public abstract String getCommandSenderName();

    /** getEntityName */
    @Override
    public String func_70023_ak() {
        return this.getCommandSenderName();
    }
}
