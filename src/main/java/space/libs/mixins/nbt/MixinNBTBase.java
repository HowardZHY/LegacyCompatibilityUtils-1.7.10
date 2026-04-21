package space.libs.mixins.nbt;

import net.minecraft.crash.*;
import net.minecraft.nbt.*;
import net.minecraft.util.ReportedException;
import org.spongepowered.asm.mixin.*;
import space.libs.util.MappedName;
import space.libs.util.cursedmixinextensions.annotations.*;

import java.io.*;

@Mixin(NBTBase.class)
public abstract class MixinNBTBase implements INBTBase {

    @Shadow
    @Override
    public abstract void write(DataOutput output);

    @Shadow
    @Override
    public abstract void read(DataInput input, int depth, NBTSizeTracker sizeTracker);

    @Shadow
    @Override
    public abstract String toString();

    @Shadow
    @Override
    public abstract byte getId();

    @Shadow
    @Override
    public abstract String getString();

    @Shadow
    @Override
    public abstract NBTBase copy();

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
    public void func_74735_a(DataInput input, int depth) {}

    @MappedName(value = "getTagName", until = "1.6.4")
    @Public
    private static String func_74736_a(byte id) {
        return INBTBase.func_193581_j(id);
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
        if (this.field_74741_a == null) {
            return "";
        }
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
