package space.libs.mixins.entity;

import net.minecraft.entity.IEntityOwnableName;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.CompatLib;

import java.util.UUID;

@SuppressWarnings("unused")
@Mixin(EntityTameable.class)
public abstract class MixinEntityTameable implements IEntityOwnableName {

    @Shadow
    public abstract String func_152113_b();

    @Shadow
    public abstract void func_152115_b(String uuid);

    /** getOwnerName */
    public String func_70905_p() {
        String name;
        try {
            UUID uuid = UUID.fromString(func_152113_b());
            EntityPlayer player = MinecraftServer.getServer().getEntityWorld().getPlayerEntityByUUID(uuid);
            name = player.getCommandSenderName();
        } catch (Exception e) {
            CompatLib.LOGGER.error("Cannot get tamable entity owner's name. Return owner's uuid instead.");
            e.printStackTrace();
            return this.func_152113_b();
        }
        return name;
    }

    /** setOwner */
    public void func_70910_a(String name) {
        EntityPlayer player = MinecraftServer.getServer().getEntityWorld().getPlayerEntityByName(name);
        if (player != null) {
            String uuid = player.getUniqueID().toString();
            this.func_152115_b(uuid);
        } else {
            CompatLib.LOGGER.warn("A Legacy Mod tried to set tamable entity owner as someone not online. This is unsupported.");
        }
    }

}
