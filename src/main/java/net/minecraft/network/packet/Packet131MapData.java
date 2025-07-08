package net.minecraft.network.packet;

import space.libs.util.MappedName;

@SuppressWarnings("unused")
public class Packet131MapData extends Packet {

    @MappedName("itemID")
    public short field_73438_a;

    @MappedName("uniqueID")
    public short field_73436_b;

    @MappedName("itemData")
    public byte[] field_73437_c;

    public Packet131MapData() {
        this.field_73287_r = true;
    }

    public Packet131MapData(short iid, short uid, byte[] data) {
        this.field_73287_r = true;
        this.field_73438_a = iid;
        this.field_73436_b = uid;
        this.field_73437_c = data;
    }

    public int getItemID() {
        return field_73438_a;
    }
}
