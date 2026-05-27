package space.libs.mixins.forge;

import cpw.mods.fml.common.network.internal.FMLMessage;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = FMLMessage.EntityMessage.class, remap = false)
public class MixinFMLMessageEntityMessage {

    @Shadow
    Entity entity;

    @Shadow
    int entityId;

    public Entity getEntity() {
        return entity;
    }

    public int getEntityId() {
        return entityId;
    }
}
