package net.minecraft.nbt;

import net.minecraft.util.*;
import space.libs.util.MappedName;

import java.io.*;

@SuppressWarnings("unused")
public interface INBTBase {

    /** @apiNote field_82578_b */
    String[] NBT_TYPES = new String[]{"END", "BYTE", "SHORT", "INT", "LONG", "FLOAT", "DOUBLE", "BYTE[]", "STRING", "LIST", "COMPOUND", "INT[]", "LONG[]"};

    EnumChatFormatting field_197638_b = EnumChatFormatting.AQUA;

    EnumChatFormatting field_197639_c = EnumChatFormatting.GREEN;

    EnumChatFormatting field_197640_d = EnumChatFormatting.GOLD;

    EnumChatFormatting field_197641_e = EnumChatFormatting.RED;

    /** @apiNote Legacy read method */
    @MappedName("load")
    default void func_74735_a(DataInput input, int depth) {}

    @MappedName("setName")
    default NBTBase func_74738_o(String name) {
        return (NBTBase) this;
    }

    @MappedName("getName")
    default String func_74740_e() {
        return "";
    }

    void write(DataOutput output);

    void read(DataInput input, int depth, NBTSizeTracker sizeTracker);

    String toString();

    byte getId();

    @MappedName("createNewByType")
    static INBTBase func_150284_a(byte id) {
        return (INBTBase) NBTBase.createNewByType(id);
    }

    @MappedName("getTypeName")
    static String func_193581_j(int id) {
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
            case 12:
                return "TAG_Long_Array";
            case 99:
                return "Any Numeric Tag";
        }
        return "UNKNOWN";
    }

    NBTBase copy();

    default INBTBase func_74737_b() {
        return (INBTBase) this.copy();
    }

    default String getString() {
        return this.toString();
    }

    default IChatComponent func_197637_c() {
        return func_199850_a("", 0);
    }

    default IChatComponent func_199850_a(String str, int i) {
        return new ChatComponentText(str);
    }
}
