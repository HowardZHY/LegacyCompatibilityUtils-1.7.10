package space.libs.mixins.entity;

import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.UUID;

@SuppressWarnings("unused")
@Mixin(EntityHorse.class)
public abstract class MixinEntityHorse extends MixinEntityLiving {

    @Shadow
    public abstract String getCommandSenderName();

    @Shadow
    public abstract String func_152119_ch();

    @Shadow
    public abstract void func_152120_b(String uuid);

    /** getEntityName */
    @Override
    public String func_70023_ak() {
        return this.getCommandSenderName();
    }

    /** setOwnerName */
    public void func_110213_b(String name) {
        EntityPlayer player = MinecraftServer.getServer().getEntityWorld().getPlayerEntityByName(name);
        if (player != null) {
            String uuid = player.getUniqueID().toString();
            this.func_152120_b(uuid);
        } else {
            System.out.println("[CompatLib] A Legacy Mod tried to set horse owner as someone not online. This is unsupported.");
        }
    }

    /** getOwnerName */
    public String func_142019_cb() {
        String name;
        try {
            UUID uuid = UUID.fromString(func_152119_ch());
            EntityPlayer player = MinecraftServer.getServer().getEntityWorld().getPlayerEntityByUUID(uuid);
            name = player.getCommandSenderName();
        } catch (Exception e) {
            System.out.println("[CompatLib] Cannot get horse owner's name. Return owner's uuid instead.");
            return this.func_152119_ch();
        }
        return name;
    }

}
