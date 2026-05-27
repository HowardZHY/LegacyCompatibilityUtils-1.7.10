package space.libs.interfaces;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;

import java.util.List;

public interface IEntitySpawnMessage {

    int getModEntityTypeId();

    int getRawX();

    int getRawY();

    int getRawZ();

    double getScaledX();

    double getScaledY();

    double getScaledZ();

    float getScaledYaw();

    float getScaledPitch();

    float getScaledHeadYaw();

    int getThrowerId();

    double getSpeedScaledX();

    double getSpeedScaledY();

    double getSpeedScaledZ();

    List<?> getDataWatcherList();

    ByteBuf getDataStream();

    Entity getEntity();

    int getEntityId();

}
