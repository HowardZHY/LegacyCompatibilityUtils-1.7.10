package space.libs.mixins.forge;

import cpw.mods.fml.common.network.internal.FMLMessage;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.interfaces.IEntityAdjustMessage;

@Mixin(value = FMLMessage.EntityAdjustMessage.class, remap = false)
public class MixinFMLMessageEntityAdjustMessage extends MixinFMLMessageEntityMessage implements IEntityAdjustMessage {

    @Shadow
    int serverX;

    @Shadow
    int serverY;

    @Shadow
    int serverZ;

    @Override
    public int getServerX() {
        return serverX;
    }

    @Override
    public int getServerY() {
        return serverY;
    }

    @Override
    public int getServerZ() {
        return serverZ;
    }

    @Override
    public Entity getEntity() {
        return super.getEntity();
    }

    @Override
    public int getEntityId() {
        return super.getEntityId();
    }

}
