package space.libs.mixins.entity;

import net.minecraft.entity.item.EntityMinecart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.util.MappedName;

@Mixin(EntityMinecart.class)
public abstract class MixinEntityMinecart extends MixinEntity {

    @Shadow
    @Override
    public abstract String getCommandSenderName();

    @MappedName("getEntityName")
    @Override
    public String func_70023_ak() {
        return this.getCommandSenderName();
    }

}
