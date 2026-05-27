package space.libs.interfaces;

import net.minecraft.entity.Entity;

public interface IEntityAdjustMessage {

    int getServerX();

    int getServerY();

    int getServerZ();

    Entity getEntity();

    int getEntityId();

}
