package net.minecraft.block;

@SuppressWarnings("unused")
public class RedstoneUpdateInfo extends BlockRedstoneTorch.Toggle {

    public int field_73664_a;

    public int field_73662_b;

    public int field_73663_c;

    public long field_73661_d;

    public RedstoneUpdateInfo(int x, int y, int z, long updateTime) {
        super(x, y, z, updateTime);
        this.field_73664_a = x;
        this.field_73662_b = y;
        this.field_73663_c = z;
        this.field_73661_d = updateTime;
    }
}
