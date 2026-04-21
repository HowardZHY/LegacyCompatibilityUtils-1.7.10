package net.minecraft.nbt;

import space.libs.util.MappedName;

import java.io.*;
import java.util.*;

@SuppressWarnings("unused")
public class NBTTagLongArray extends NBTBase implements INBTBase {

    @MappedName("data")
    public long[] field_193587_b;

    public NBTTagLongArray(long[] longs) {
        this.field_193587_b = longs;
    }

    public NBTTagLongArray(List<Long> list) {
        this(func_193586_a(list));
    }

    @MappedName("toArray")
    public static long[] func_193586_a(List<Long> list) {
        long[] longs = new long[list.size()];
        for (int i = 0; i < list.size(); ++i) {
            Long l = list.get(i);
            longs[i] = l == null ? 0L : l;
        }
        return longs;
    }

    @Override
    public void write(DataOutput output) {
        try {
            output.writeInt(this.field_193587_b.length);
            for (long i : this.field_193587_b) {
                output.writeLong(i);
            }
        } catch (IOException e) {
            System.out.println("Exception while reading NBT data input : " + e);
        }
    }

    @Override
    public void read(DataInput input, int depth, NBTSizeTracker sizeTracker) {
        try {
            sizeTracker.addSpaceRead(192L);
            int i = input.readInt();
            sizeTracker.addSpaceRead(64L * i);
            this.field_193587_b = new long[i];
            for (int j = 0; j < i; ++j) {
                this.field_193587_b[j] = input.readLong();
            }
        } catch (IOException e) {
            System.out.println("Exception while reading NBT data input : " + e);
        }
    }

    @Override
    public byte getId() {
        return 12;
    }

    @Override
    public String toString() {
        StringBuilder stringbuilder = new StringBuilder("[L;");
        for (int i = 0; i < this.field_193587_b.length; ++i) {
            if (i != 0) {
                stringbuilder.append(',');
            }
            stringbuilder.append(this.field_193587_b[i]).append('L');
        }
        return stringbuilder.append(']').toString();
    }

    @Override
    public NBTTagLongArray copy() {
        long[] along = new long[this.field_193587_b.length];
        System.arraycopy(this.field_193587_b, 0, along, 0, this.field_193587_b.length);
        return new NBTTagLongArray(along);
    }

    @Override
    public boolean equals(Object object) {
        return super.equals(object) && Arrays.equals(this.field_193587_b, ((NBTTagLongArray) object).field_193587_b);
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ Arrays.hashCode(this.field_193587_b);
    }
}
