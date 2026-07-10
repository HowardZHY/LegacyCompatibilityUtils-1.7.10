package space.libs.mixins.entity;

import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.util.MappedName;

import java.util.UUID;

@SuppressWarnings("unused")
@Mixin(EntityHorse.class)
public abstract class MixinEntityHorse extends MixinEntityAnimal {

    @Shadow
    @Override
    public abstract String getCommandSenderName();

    @Shadow
    public abstract String func_152119_ch();

    @Shadow
    public abstract void func_152120_b(String uuid);

    @MappedName("getEntityName")
    @Override
    public String func_70023_ak() {
        return this.getCommandSenderName();
    }

    @MappedName("setOwnerName")
    public void func_110213_b(String name) {
        World world = this.worldObj;
        if (world != null) {
            EntityPlayer player = this.worldObj.getPlayerEntityByName(name);
            if (player != null) {
                String uuid = player.getUniqueID().toString();
                this.func_152120_b(uuid);
                return;
            }
        }
        System.out.println("[CompatLib] A Legacy Mod tried to set horse owner as someone not online. This is unsupported.");
    }

    @MappedName("getOwnerName")
    public String func_142019_cb() {
        String name = this.func_152119_ch();
        try {
            UUID uuid = UUID.fromString(name);
            EntityPlayer player = this.worldObj.getPlayerEntityByUUID(uuid);
            name = player.getCommandSenderName();
        } catch (Exception e) {
            System.out.println("[CompatLib] Cannot get horse owner's name. Return owner's UUID instead.");
        }
        return name;
    }

}
