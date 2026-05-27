package space.libs.mixins.forge;

import cpw.mods.fml.common.network.internal.FMLMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.interfaces.IEntitySpawnMessage;

import java.util.List;

@Mixin(value = FMLMessage.EntitySpawnMessage.class, remap = false)
public class MixinFMLMessageEntitySpawnMessage extends MixinFMLMessageEntityMessage implements IEntitySpawnMessage {

    @Shadow int modEntityTypeId, rawX, rawY, rawZ, throwerId;

    @Shadow double scaledX, scaledY, scaledZ, speedScaledX, speedScaledY, speedScaledZ;

    @Shadow float scaledYaw, scaledPitch, scaledHeadYaw;

    @Shadow List<?> dataWatcherList;

    @Shadow
    ByteBuf dataStream;

    @Override
    public int getModEntityTypeId() {
        return this.modEntityTypeId;
    }

    @Override
    public int getRawX() {
        return this.rawX;
    }

    @Override
    public int getRawY() {
        return this.rawY;
    }

    @Override
    public int getRawZ() {
        return this.rawZ;
    }

    @Override
    public double getScaledX() {
        return this.scaledX;
    }

    @Override
    public double getScaledY() {
        return this.scaledY;
    }

    @Override
    public double getScaledZ() {
        return this.scaledZ;
    }

    @Override
    public float getScaledYaw() {
        return this.scaledYaw;
    }

    @Override
    public float getScaledPitch() {
        return this.scaledPitch;
    }

    @Override
    public float getScaledHeadYaw() {
        return this.scaledHeadYaw;
    }

    @Override
    public int getThrowerId() {
        return this.throwerId;
    }

    @Override
    public double getSpeedScaledX() {
        return this.speedScaledX;
    }

    @Override
    public double getSpeedScaledY() {
        return this.speedScaledY;
    }

    @Override
    public double getSpeedScaledZ() {
        return this.speedScaledZ;
    }

    @Override
    public List<?> getDataWatcherList() {
        return this.dataWatcherList;
    }

    @Override
    public ByteBuf getDataStream() {
        return dataStream;
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
