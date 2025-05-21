package space.libs.mixins.forge;

import com.google.common.collect.BiMap;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.interfaces.IEntityRegistry;
import space.libs.util.cursedmixinextensions.annotations.Public;

@Mixin(value = EntityRegistry.class, remap = false)
public class MixinEntityRegistry implements IEntityRegistry {

    @Shadow
    private BiMap<Class<? extends Entity>, EntityRegistry.EntityRegistration> entityClassRegistrations;

    @Shadow
    public static EntityRegistry instance() {
        throw new AbstractMethodError();
    }

    @Shadow
    private void doModEntityRegistration(Class<? extends Entity> entityClass, String entityName, int id, Object mod, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {}

    public BiMap<Class<? extends Entity>, EntityRegistry.EntityRegistration> getEntityClassRegistrations() {
        return entityClassRegistrations;
    }

    public void doModRegistration(Class<? extends Entity> entityClass, String entityName, int id, Object mod, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
        this.doModEntityRegistration(entityClass, entityName, id, mod, trackingRange, updateFrequency, sendsVelocityUpdates);
    }

    public EntityRegistry.EntityRegistration registerModLoader(Object mod, Class<? extends Entity> entityClass, int entityTypeId, int updateRange, int updateInterval, boolean sendVelocityInfo) {
        String entityName = (String) EntityList.classToStringMapping.get(entityClass);
        if (entityName == null) {
            throw new IllegalArgumentException(String.format("The ModLoader mod %s has tried to register an entity tracker for a non-existent entity type %s", Loader.instance().activeModContainer().getModId(), entityClass.getCanonicalName()));
        }
        this.doModEntityRegistration(entityClass, entityName, entityTypeId, mod, updateRange, updateInterval, sendVelocityInfo);
        return this.entityClassRegistrations.get(entityClass);
    }

    @Public
    private static EntityRegistry.EntityRegistration registerModLoaderEntity(Object mod, Class<? extends Entity> entityClass, int entityTypeId, int updateRange, int updateInterval, boolean sendVelocityInfo) {
        IEntityRegistry accessor = (IEntityRegistry) instance();
        return accessor.registerModLoader(mod, entityClass, entityTypeId, updateRange, updateInterval, sendVelocityInfo);
    }
}
