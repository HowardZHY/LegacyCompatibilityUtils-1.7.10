package space.libs.mixins.world;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.SaveHandler;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.*;
import space.libs.util.MappedName;

import java.io.File;

@Mixin(SaveHandler.class)
public abstract class MixinSaveHandler {

    @Final
    @Shadow
    private static Logger logger;

    @Final
    @Shadow
    private File playersDirectory;

    @Shadow
    public abstract NBTTagCompound readPlayerData(EntityPlayer player);

    @MappedName("getPlayerData")
    public NBTTagCompound func_75764_a(String name) {
        EntityPlayer player;
        try {
            player = MinecraftServer.getServer().getEntityWorld().getPlayerEntityByName(name);
        } catch (Exception exception) {
            logger.warn("[CompatLib] Player " + name + " is offline or doesn't exist, cannot load player data !");
            return null;
        }
        return this.readPlayerData(player);
    }

}
