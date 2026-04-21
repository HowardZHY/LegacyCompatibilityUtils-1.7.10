package space.libs.mixins.nbt;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.*;
import net.minecraft.util.ReportedException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.util.MappedName;
import space.libs.util.cursedmixinextensions.annotations.Public;

import java.io.DataInput;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

@SuppressWarnings({"unused"})
@Mixin(NBTTagCompound.class)
public abstract class MixinNBTTagCompound extends MixinNBTBase implements INBTBase {

    @Shadow
    private Map<String, NBTBase> tagMap;

    @Shadow
    private CrashReport createCrashReport(String key, int i, ClassCastException e) {
        throw new AbstractMethodError();
    }

    @MappedName(value = "load", until = "1.7.2")
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

    @MappedName(value = "getTags", until = "1.6.4")
    public Collection<NBTBase> func_74758_c() {
        return this.tagMap.values();
    }

    @MappedName(value = "getTagList", until = "1.6.4")
    public NBTTagList func_74761_m(String key) {
        try {
            return !this.tagMap.containsKey(key) ? new NBTTagList() : (NBTTagList)this.tagMap.get(key);
        } catch (ClassCastException e) {
            throw new ReportedException(this.createCrashReport(key, 9, e));
        }
    }

    @MappedName(value = "setCompoundTag", until = "1.6.4")
    public void func_74766_a(String key, NBTTagCompound value) {
        this.tagMap.put(key, ((INBTBase) value).func_74738_o(key));
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
            crashreportcategory.addCrashSection("Tag type", b);
            throw new ReportedException(crashreport);
        }
        return nbtbase;
    }

}
