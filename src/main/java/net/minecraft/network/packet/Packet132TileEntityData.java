package net.minecraft.network.packet;

import net.minecraft.nbt.NBTTagCompound;

import java.io.*;

public class Packet132TileEntityData extends Packet {

    public int field_73334_a;

    public int field_73332_b;

    public int field_73333_c;

    public int field_73330_d;

    public NBTTagCompound field_73331_e;

    public Packet132TileEntityData() {
        this.field_73287_r = true;
    }

    public Packet132TileEntityData(int x, int y, int z, int actionType, NBTTagCompound data) {
        this.field_73287_r = true;
        this.field_73334_a = x;
        this.field_73332_b = y;
        this.field_73333_c = z;
        this.field_73330_d = actionType;
        this.field_73331_e = data;
    }

    @Override
    public void func_73267_a(DataInput input) throws IOException {
        this.field_73334_a = input.readInt();
        this.field_73332_b = input.readShort();
        this.field_73333_c = input.readInt();
        this.field_73330_d = input.readByte();
        this.field_73331_e = func_73283_d(input);
    }

    @Override
    public void func_73273_a(DataOutput output) throws IOException {
        output.writeInt(this.field_73334_a);
        output.writeShort(this.field_73332_b);
        output.writeInt(this.field_73333_c);
        output.writeByte((byte)this.field_73330_d);
        func_73275_a(this.field_73331_e, output);
    }

    @Override
    public void func_73279_a(NetHandler handler) {
        //handler.func_72468_a(this);
    }

    public int func_73284_a() {
        return 25;
    }
}
