package space.libs.mixins.entity;

import net.minecraft.entity.EntityLiving;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.util.MappedName;

@Mixin(EntityLiving.class)
public abstract class MixinEntityLiving extends MixinEntityLivingBase {

    @Shadow
    public abstract String getCommandSenderName();

    @MappedName("getEntityName")
    @Override
    public String func_70023_ak() {
        return this.getCommandSenderName();
    }
}
