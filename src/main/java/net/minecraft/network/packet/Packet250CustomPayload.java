package net.minecraft.network.packet;

import space.libs.util.MappedName;

public class Packet250CustomPayload extends Packet {

    @MappedName("channel")
    public String field_73630_a;

    @MappedName("length")
    public int field_73628_b;

    @MappedName("data")
    public byte[] field_73629_c;

    public Packet250CustomPayload() {}

    public Packet250CustomPayload(String type, byte[] data) {
        this.field_73630_a = type;
        this.field_73629_c = data;
        if (data != null) {
            this.field_73628_b = data.length;
            if (this.field_73628_b > 32767) {
                throw new IllegalArgumentException("Payload may not be larger than 32k");
            }
        }
    }
}
