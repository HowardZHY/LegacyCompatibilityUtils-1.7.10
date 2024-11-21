package space.libs.mixins.entity;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@SuppressWarnings("all")
@Mixin(Entity.class)
public abstract class MixinEntity {

    /** func_70005_c_ */
    @Shadow
    public abstract String getCommandSenderName();

    /** getEntityName */
    public String func_70023_ak() {
        return this.getCommandSenderName();
    }
}
