package net.minecraft.nbt;

import java.io.DataInput;

@SuppressWarnings("unused")
public interface INBTBase {

    /** field_82578_b */
    String[] NBT_TYPES = new String[]{"END", "BYTE", "SHORT", "INT", "LONG", "FLOAT", "DOUBLE", "BYTE[]", "STRING", "LIST", "COMPOUND", "INT[]", "LONG[]"};

    /** load */
    void func_74735_a(DataInput paramDataInput, int paramInt);
}
