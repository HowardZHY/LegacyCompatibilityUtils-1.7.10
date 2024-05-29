package space.libs.mixins;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.SaveHandler;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.io.File;
import java.io.FileInputStream;

@SuppressWarnings("all")
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

    /** getPlayerData */
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
