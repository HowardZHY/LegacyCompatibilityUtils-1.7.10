package net.minecraft.nbt;

import space.libs.util.MappedName;

import java.io.DataInput;

@SuppressWarnings("unused")
public interface INBTBase {

    /** field_82578_b */
    String[] NBT_TYPES = new String[]{"END", "BYTE", "SHORT", "INT", "LONG", "FLOAT", "DOUBLE", "BYTE[]", "STRING", "LIST", "COMPOUND", "INT[]", "LONG[]"};

    @MappedName("load")
    void func_74735_a(DataInput paramDataInput, int paramInt);

    @MappedName("setName")
    NBTBase func_74738_o(String name);

    @MappedName("getName")
    String func_74740_e();
}
