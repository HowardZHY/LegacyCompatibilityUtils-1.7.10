package space.libs.mixins.interfaces;

import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.IEntityOwnableName;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import space.libs.CompatLib;
import space.libs.interfaces.IEntity;

import java.util.UUID;

@SuppressWarnings("unused")
@Mixin(IEntityOwnable.class)
public interface MixinIEntityOwnable extends IEntityOwnableName {

    /**
     * @implNote Situations <br>
     * 1. Legacy Mod extends EntityTameable <br>
     * 2. Legacy Mod doesn't extends EntityTameable but implements IEntityOwnable <br>
     * 3. Modern Mod extends EntityTameable <br>
     * 4. Modern Mod doesn't extends EntityTameable but implements IEntityOwnable
     */
    @Override
    default String func_70905_p() {
        String name = this.func_152113_b();
        try {
            UUID uuid = UUID.fromString(name);
            EntityPlayer player = this.compatlib$getWorld().getPlayerEntityByUUID(uuid);
            name = player.getCommandSenderName();
        } catch (Exception e) {
            CompatLib.LOGGER.error("Cannot get tamable entity owner's name. Return owner's UUID instead.");
            e.printStackTrace();
        }
        return name;
    }

    /**
     * @author HowardZHY
     * @reason No longer abstract
     */
    @SuppressWarnings("OverwriteModifiers")
    @Override
    @Overwrite
    default String func_152113_b() {
        String name = this.func_70905_p();
        World world = this.compatlib$getWorld();
        if (world != null) {
            EntityPlayer player = world.getPlayerEntityByName(name);
            if (player != null) {
                return player.getUniqueID().toString();
            }
        }
        CompatLib.LOGGER.warn("A Legacy Mod tried to get tamable entity owner by name as someone not online. This is unsupported.");
        return "";
    }

    default World compatlib$getWorld() {
        if (this instanceof IEntity) {
            return ((IEntity) this).compatlib$getWorld();
        }
        return MinecraftServer.getServer().getEntityWorld();
    }
}
