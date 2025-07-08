package net.minecraft.util;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class AABBPool {

    public final int field_72306_a;

    public final int field_72304_b;

    public final List<AxisAlignedBB> field_72305_c = new ArrayList<>();

    public int field_72302_d;

    public int field_72303_e;

    public int field_72301_f;

    public AABBPool(int paramInt1, int paramInt2) {
        this.field_72306_a = paramInt1;
        this.field_72304_b = paramInt2;
    }

    public AxisAlignedBB func_72299_a(double x1, double y1, double z1, double x2, double y2, double z2) {
        AxisAlignedBB axisAlignedBB;
        if (this.field_72302_d >= this.field_72305_c.size()) {
            axisAlignedBB = new AxisAlignedBB(x1, y1, z1, x2, y2, z2);
            this.field_72305_c.add(axisAlignedBB);
        } else {
            axisAlignedBB = this.field_72305_c.get(this.field_72302_d);
            axisAlignedBB.setBounds(x1, y1, z1, x2, y2, z2);
        }
        this.field_72302_d++;
        return axisAlignedBB;
    }

    public void func_72298_a() {
        if (this.field_72302_d > this.field_72303_e)
            this.field_72303_e = this.field_72302_d;
        if (this.field_72301_f++ == this.field_72306_a) {
            int i = Math.max(this.field_72303_e, this.field_72305_c.size() - this.field_72304_b);
            while (this.field_72305_c.size() > i)
                this.field_72305_c.remove(i);
            this.field_72303_e = 0;
            this.field_72301_f = 0;
        }
        this.field_72302_d = 0;
    }

    public void func_72300_b() {
        this.field_72302_d = 0;
        this.field_72305_c.clear();
    }

    public int func_83013_c() {
        return this.field_72305_c.size();
    }

    public int func_83012_d() {
        return this.field_72302_d;
    }
}
