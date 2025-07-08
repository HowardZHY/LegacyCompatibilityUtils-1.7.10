package space.libs.mixins.nbt;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagEnd;
import net.minecraft.util.ReportedException;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.nbt.INBTBase;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.util.MappedName;
import space.libs.util.cursedmixinextensions.annotations.NewConstructor;
import space.libs.util.cursedmixinextensions.annotations.Public;
import space.libs.util.cursedmixinextensions.annotations.ShadowConstructor;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@Mixin(NBTBase.class)
public abstract class MixinNBTBase implements INBTBase {

    @Shadow
    public static NBTBase createNewByType(byte id) {
        throw new AbstractMethodError();
    }

    @ShadowConstructor
    public void NBTBase() {}

    /**
     * @implNote 1.6.4
     */
    @NewConstructor
    public void NBTBase(String name) {
        NBTBase();
        if (name == null) {
            this.field_74741_a = "";
        } else {
            this.field_74741_a = name;
        }
    }

    @MappedName(value = "name", until = "1.6.4")
    public String field_74741_a;

    @MappedName(value = "writeNamedTag", until = "1.6.4")
    @Public
    private static void func_74731_a(NBTBase nbt, DataOutput output) throws IOException {
        output.writeByte(nbt.getId());
        if (nbt.getId() == 0) {
            return;
        }
        INBTBase accessor = (INBTBase) nbt;
        output.writeUTF(accessor.func_74740_e());
        nbt.write(output);
    }

    @MappedName(value = "newTag", until = "1.6.4")
    @Public
    private static NBTBase func_74733_a(byte id, String name) {
        NBTBase nbt = createNewByType(id);
        ((INBTBase) nbt).func_74738_o(name);
        return nbt;
    }

    @MappedName(value = "load", until = "1.7.2")
    public abstract void func_74735_a(DataInput paramDataInput, int paramInt);

    @MappedName(value = "getTagName", until = "1.6.4")
    @Public
    private static String func_74736_a(byte id) {
        switch (id) {
            case 0:
                return "TAG_End";
            case 1:
                return "TAG_Byte";
            case 2:
                return "TAG_Short";
            case 3:
                return "TAG_Int";
            case 4:
                return "TAG_Long";
            case 5:
                return "TAG_Float";
            case 6:
                return "TAG_Double";
            case 7:
                return "TAG_Byte_Array";
            case 8:
                return "TAG_String";
            case 9:
                return "TAG_List";
            case 10:
                return "TAG_Compound";
            case 11:
                return "TAG_Int_Array";
        }
        return "UNKNOWN";
    }

    @MappedName(value = "setName", until = "1.6.4")
    public NBTBase func_74738_o(String name) {
        if (name == null) {
            this.field_74741_a = "";
        } else {
            this.field_74741_a = name;
        }
        return (NBTBase) (Object) this;
    }

    @MappedName(value = "getName", until = "1.6.4")
    public String func_74740_e() {
        if (this.field_74741_a == null)
            return "";
        return this.field_74741_a;
    }

    @Public
    private static NBTBase func_130104_b(DataInput input, int depth) throws IOException {
        byte id = input.readByte();
        if (id == 0) {
            return new NBTTagEnd();
        }
        String str = input.readUTF();
        NBTBase nbt = func_74733_a(id, str);
        try {
            ((INBTBase) nbt).func_74735_a(input, depth);
        } catch (Exception e) {
            CrashReport crashReport = CrashReport.makeCrashReport(e, "Loading NBT data");
            CrashReportCategory crashReportCategory = crashReport.makeCategory("NBT Tag");
            crashReportCategory.addCrashSection("Tag name", str);
            crashReportCategory.addCrashSection("Tag type", id);
            throw new ReportedException(crashReport);
        }
        return nbt;
    }
}
