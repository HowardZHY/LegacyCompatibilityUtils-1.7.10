package space.libs.mixins;

import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@SuppressWarnings("unused")
@Mixin(EntityTameable.class)
public abstract class MixinEntityTameable {

    @Shadow
    public abstract String func_152113_b();

    @Shadow
    public void func_152115_b(String uuid) {}

    /** getOwnerName */
    public String func_70905_p() {
        return this.func_152113_b();
    }

    /** setOwner */
    public void func_70910_a(String name) {
        EntityPlayer player = MinecraftServer.getServer().getEntityWorld().getPlayerEntityByName(name);
        if (player != null) {
            String uuid = player.getUniqueID().toString();
            this.func_152115_b(uuid);
        } else {
            System.out.println("A Mod tried to set tameable entity owner as someone not online. This is unsupported.");
        }
    }

}
