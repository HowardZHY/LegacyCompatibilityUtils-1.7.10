package net.minecraft.network.packet;

import space.libs.util.MappedName;

import java.io.*;

public class Packet250CustomPayload extends Packet {

    @MappedName("channel")
    public String field_73630_a;

    @MappedName("length")
    public int field_73628_b;

    @MappedName("data")
    public byte[] field_73629_c;

    public Packet250CustomPayload() {
        super();
    }

    public Packet250CustomPayload(String type, byte[] data) {
        super(type, data);
        this.field_73630_a = type;
        this.field_73629_c = data;
        if (data != null) {
            this.field_73628_b = data.length;
            if (this.field_73628_b > 32767) {
                throw new IllegalArgumentException("Payload may not be larger than 32k");
            }
        }
    }

    @Override
    public void func_73267_a(DataInput input) throws IOException {
        this.field_73630_a = func_73282_a(input, 20);
        this.field_73628_b = input.readShort();
        if (this.field_73628_b > 0 && this.field_73628_b < 32767) {
            this.field_73629_c = new byte[this.field_73628_b];
            input.readFully(this.field_73629_c);
        }
    }

    @Override
    public void func_73273_a(DataOutput output) throws IOException {
        func_73271_a(this.field_73630_a, output);
        output.writeShort((short)this.field_73628_b);
        if (this.field_73629_c != null) {
            output.write(this.field_73629_c);
        }
    }

    @Override
    public void func_73279_a(NetHandler handler) {
        handler.func_72501_a(this);
    }

    public int func_73284_a() {
        return 2 + this.field_73630_a.length() * 2 + 2 + this.field_73628_b;
    }
}
