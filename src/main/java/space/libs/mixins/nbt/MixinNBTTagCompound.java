package space.libs.mixins.nbt;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ReportedException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.nbt.INBTBase;
import space.libs.util.cursedmixinextensions.annotations.Public;

import java.io.DataInput;
import java.io.IOException;
import java.util.Map;

@SuppressWarnings("all")
@Mixin(NBTTagCompound.class)
public class MixinNBTTagCompound extends MixinNBTBase implements INBTBase {
    @Shadow
    private Map tagMap;

    /** load */
    public void func_74735_a(DataInput input, int depth) {
        if (depth > 512) {
            throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
        }
        this.tagMap.clear();
        byte b;
        while ((b = func_150300_a(input)) != 0) {
            String str = func_150294_b(input);
            NBTBase nBTBase = func_150293_a(b, str, input, depth + 1);
            this.tagMap.put(str, nBTBase);
        }
    }

    @Public
    private static byte func_150300_a(DataInput input) {
        try {
            return input.readByte();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Public
    private static String func_150294_b(DataInput input) {
        try {
            return input.readUTF();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Public
    private static NBTBase func_150293_a(byte b, String s, DataInput input, int depth) {
        NBTBase nbtbase = NBTBase.createNewByType(b);
        INBTBase accessor = (INBTBase) nbtbase;
        try {
            accessor.func_74735_a(input, depth);
        } catch (Exception exception) {
            CrashReport crashreport = CrashReport.makeCrashReport(exception, "Loading NBT data");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("NBT Tag");
            crashreportcategory.addCrashSection("Tag name", "[UNNAMED TAG]");
            crashreportcategory.addCrashSection("Tag type", Byte.valueOf(b));
            throw new ReportedException(crashreport);
        }
        return nbtbase;
    }

}
