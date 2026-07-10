package space.libs.mixins.entity;

import net.minecraft.entity.passive.EntityOcelot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.util.MappedName;

@Mixin(EntityOcelot.class)
public abstract class MixinEntityOcelot extends MixinEntityTameable {

    @Shadow
    public abstract String getCommandSenderName();

    @MappedName("getEntityName")
    @Override
    public String func_70023_ak() {
        return this.getCommandSenderName();
    }
}
