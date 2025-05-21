package space.libs.fml;

import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.network.internal.FMLMessage;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.Entity;

import java.lang.reflect.Field;

@SuppressWarnings("unused")
public class EntitySpawnPacket extends FMLMessage.EntitySpawnMessage {

    public EntitySpawnPacket() {
        super();
    }

    public EntitySpawnPacket(EntityRegistry.EntityRegistration er, Entity entity, ModContainer modContainer) {
        super(er, entity, modContainer);
    }

    public double getScaled(int flag) {
        Field f;
        double d = 0.0;
        try {
            switch (flag) {
                case 1:
                    f = EntitySpawnMessage.class.getDeclaredField("scaledX");
                    f.setAccessible(true);
                    d = (double) f.get(this);
                case 2:
                    f = EntitySpawnMessage.class.getDeclaredField("scaledY");
                    f.setAccessible(true);
                    d = (double) f.get(this);
                case 3:
                    f = EntitySpawnMessage.class.getDeclaredField("scaledZ");
                    f.setAccessible(true);
                    d = (double) f.get(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }
}
