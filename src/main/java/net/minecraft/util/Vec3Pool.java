package net.minecraft.util;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
public class Vec3Pool {
    public final int field_72351_a;

    public final int field_72349_b;

    public final List field_72350_c = new ArrayList();

    public int field_72347_d;

    public int field_72348_e;

    public int field_72346_f;
    public Vec3Pool(int i, int j) {
        this.field_72351_a = i;
        this.field_72349_b = j;
    }

    public Vec3 func_72345_a(double paramDouble1, double paramDouble2, double paramDouble3) {
        Vec3 vec3;
        if (func_82589_e())
            return new Vec3(paramDouble1, paramDouble2, paramDouble3);
        if (this.field_72347_d >= this.field_72350_c.size()) {
            vec3 = new Vec3(paramDouble1, paramDouble2, paramDouble3);
            this.field_72350_c.add(vec3);
        } else {
            vec3 = (Vec3) this.field_72350_c.get(this.field_72347_d);
            vec3.setComponents(paramDouble1, paramDouble2, paramDouble3);
        }
        this.field_72347_d++;
        return vec3;
    }

    public void func_72343_a() {
        if (func_82589_e())
            return;
        if (this.field_72347_d > this.field_72348_e)
            this.field_72348_e = this.field_72347_d;
        if (this.field_72346_f++ == this.field_72351_a) {
            int i = Math.max(this.field_72348_e, this.field_72350_c.size() - this.field_72349_b);
            while (this.field_72350_c.size() > i)
                this.field_72350_c.remove(i);
            this.field_72348_e = 0;
            this.field_72346_f = 0;
        }
        this.field_72347_d = 0;
    }

    public void func_72344_b() {
        if (func_82589_e())
            return;
        this.field_72347_d = 0;
        this.field_72350_c.clear();
    }

    public int func_82591_c() {
        return this.field_72350_c.size();
    }

    public int func_82590_d() {
        return this.field_72347_d;
    }

    private boolean func_82589_e() {
        return (this.field_72349_b < 0 || this.field_72351_a < 0);
    }
}
