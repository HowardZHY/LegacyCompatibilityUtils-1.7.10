package space.libs.mixins.entity;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.util.MappedName;

@Mixin(Entity.class)
public abstract class MixinEntity {

    /** func_70005_c_ */
    @Shadow
    public abstract String getCommandSenderName();

    @MappedName("getEntityName")
    public String func_70023_ak() {
        return this.getCommandSenderName();
    }
}
