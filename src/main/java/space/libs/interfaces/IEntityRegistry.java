package space.libs.interfaces;

import com.google.common.collect.BiMap;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.Entity;

@SuppressWarnings("unused")
public interface IEntityRegistry {

    BiMap<Class<? extends Entity>, EntityRegistry.EntityRegistration> getEntityClassRegistrations();

    void doModRegistration(Class<? extends Entity> entityClass, String entityName, int id, Object mod, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates);

    EntityRegistry.EntityRegistration registerModLoader(Object mod, Class<? extends Entity> entityClass, int entityTypeId, int updateRange, int updateInterval, boolean sendVelocityInfo);

}
