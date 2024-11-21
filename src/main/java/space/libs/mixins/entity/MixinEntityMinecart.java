package space.libs.mixins.entity;

import net.minecraft.entity.item.EntityMinecart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityMinecart.class)
public abstract class MixinEntityMinecart extends MixinEntity {

    @Shadow
    public abstract String getCommandSenderName();

    /** getEntityName */
    @Override
    public String func_70023_ak() {
        return this.getCommandSenderName();
    }

}
