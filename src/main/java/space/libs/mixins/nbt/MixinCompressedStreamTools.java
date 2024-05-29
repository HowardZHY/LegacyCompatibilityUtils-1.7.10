package space.libs.mixins.nbt;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagEnd;
import net.minecraft.util.ReportedException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.nbt.INBTBase;
import space.libs.util.cursedmixinextensions.annotations.Public;

import java.io.*;
import java.util.zip.GZIPInputStream;

@SuppressWarnings("all")
@Mixin(CompressedStreamTools.class)
public abstract class MixinCompressedStreamTools {

    @Shadow
    public static NBTTagCompound read(DataInputStream p_74794_0_) throws IOException {
        throw new AbstractMethodError();
    }

    /** decompress */
    @Public
    private static NBTTagCompound func_74792_a(byte[] par0ArrayOfByte) throws IOException {
        NBTTagCompound nbttagcompound;
        DataInputStream datainputstream = new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(par0ArrayOfByte))));
        try {
            nbttagcompound = read(datainputstream);
        } finally {
            datainputstream.close();
        }
        return nbttagcompound;
    }

    @Public
    private static NBTTagCompound func_74794_a(DataInput data) throws IOException {
        NBTBase nbtbase = func_150664_a(data, 0);
        if (nbtbase instanceof NBTTagCompound) {
            return (NBTTagCompound) nbtbase;
        } else {
            throw new IOException("Root tag must be a named compound tag");
        }
    }

    @Public
    private static NBTBase func_150664_a(DataInput p_150664_0_, int p_150664_1_) throws IOException {
        byte b0 = p_150664_0_.readByte();
        if (b0 == 0) {
            return (NBTBase) new NBTTagEnd();
        }
        p_150664_0_.readUTF();
        NBTBase nbtbase = NBTBase.func_150284_a(b0);
        INBTBase accessor = (INBTBase) nbtbase;
        try {
            accessor.func_74735_a(p_150664_0_, p_150664_1_);
            return nbtbase;
        } catch (Exception exception) {
            CrashReport crashreport = CrashReport.makeCrashReport(exception, "Loading NBT data");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("NBT Tag");
            crashreportcategory.addCrashSection("Tag name", "[UNNAMED TAG]");
            crashreportcategory.addCrashSection("Tag type", Byte.valueOf(b0));
            throw new ReportedException(crashreport);
        }
    }

}
