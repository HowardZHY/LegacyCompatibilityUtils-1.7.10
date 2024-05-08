package space.libs.mixins;

import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@SuppressWarnings("unused")
@Mixin(EntityHorse.class)
public abstract class MixinEntityHorse {

    @Shadow
    public abstract String func_152119_ch();

    @Shadow
    public void func_152120_b(String uuid) {}

    /** setOwnerName */
    public void func_110213_b(String name) {
        EntityPlayer player = MinecraftServer.getServer().getEntityWorld().getPlayerEntityByName(name);
        if (player != null) {
            String uuid = player.getUniqueID().toString();
            this.func_152120_b(uuid);
        } else {
            System.out.println("A Mod tried to set horse owner as someone not online. This is unsupported.");
        }
    }

    /** getOwnerName */
    public String func_142019_cb() {
        return this.func_152119_ch();
    }

}
