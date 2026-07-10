package space.libs.mixins.entity;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.interfaces.IEntity;
import space.libs.util.MappedName;

@Mixin(Entity.class)
public abstract class MixinEntity implements IEntity {

    @Shadow
    public World worldObj;

    @Shadow
    public void setDead() {}

    /** func_70005_c_ */
    @Shadow
    public abstract String getCommandSenderName();

    @MappedName("getEntityName")
    public String func_70023_ak() {
        return this.getCommandSenderName();
    }

    @Override
    public World compatlib$getWorld() {
        return worldObj;
    }
}
