package net.minecraft.util;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
public class AABBPool {
    public final int field_72306_a;

    public final int field_72304_b;

    public final List field_72305_c = new ArrayList();

    public int field_72302_d;

    public int field_72303_e;

    public int field_72301_f;

    public AABBPool(int paramInt1, int paramInt2) {
        this.field_72306_a = paramInt1;
        this.field_72304_b = paramInt2;
    }

    public AxisAlignedBB func_72299_a(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) {
        AxisAlignedBB axisAlignedBB;
        if (this.field_72302_d >= this.field_72305_c.size()) {
            axisAlignedBB = new AxisAlignedBB(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6);
            this.field_72305_c.add(axisAlignedBB);
        } else {
            axisAlignedBB = (AxisAlignedBB) this.field_72305_c.get(this.field_72302_d);
            axisAlignedBB.setBounds(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6);
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
