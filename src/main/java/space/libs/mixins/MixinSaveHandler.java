package space.libs.mixins;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.SaveHandler;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.io.File;
import java.io.FileInputStream;

@SuppressWarnings("all")
@Mixin(SaveHandler.class)
public class MixinSaveHandler {

    @Final
    @Shadow
    private static Logger logger;

    @Final
    @Shadow
    private File playersDirectory;

    /** getPlayerData */
    public NBTTagCompound func_75764_a(String par1Str) {
        try {
            File file1 = new File(this.playersDirectory, par1Str + ".dat");
            if (file1.exists()) {
                return CompressedStreamTools.readCompressed(new FileInputStream(file1));
            }
        } catch (Exception exception) {
            logger.warn("[CompatLib] Failed to load player data for " + par1Str);
        }
        return null;
    }

}
