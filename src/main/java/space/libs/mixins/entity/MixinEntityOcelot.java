package space.libs.mixins.entity;

import net.minecraft.entity.passive.EntityOcelot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityOcelot.class)
public abstract class MixinEntityOcelot extends MixinEntityLiving {

    @Shadow
    public abstract String getCommandSenderName();

    /** getEntityName */
    @Override
    public String func_70023_ak() {
        return this.getCommandSenderName();
    }
}
