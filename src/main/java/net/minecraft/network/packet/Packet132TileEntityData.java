package net.minecraft.network.packet;

import net.minecraft.nbt.NBTTagCompound;

import java.io.DataInput;
import java.io.DataOutput;

public class Packet132TileEntityData extends Packet {

    public int field_73334_a;

    public int field_73332_b;

    public int field_73333_c;

    public int field_73330_d;

    public NBTTagCompound field_73331_e;

    public Packet132TileEntityData() {
        this.field_73287_r = true;
    }

    public Packet132TileEntityData(int p_i1483_1_, int p_i1483_2_, int p_i1483_3_, int p_i1483_4_, NBTTagCompound p_i1483_5_) {
        this.field_73287_r = true;
        this.field_73334_a = p_i1483_1_;
        this.field_73332_b = p_i1483_2_;
        this.field_73333_c = p_i1483_3_;
        this.field_73330_d = p_i1483_4_;
        this.field_73331_e = p_i1483_5_;
    }

    public void func_73267_a(DataInput p_73267_1_) {

    }

    public void func_73273_a(DataOutput p_73273_1_) {

    }

    public void func_73279_a(NetHandler p_73279_1_) {

    }

    public int func_73284_a() {
        return 25;
    }
}
