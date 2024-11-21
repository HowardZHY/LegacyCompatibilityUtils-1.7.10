package net.minecraft.util;

@SuppressWarnings("unused")
public class AABBLocalPool extends ThreadLocal<Object> {

    /** createNewDefaultPool */
    public AABBPool func_72341_a() {
        return new AABBPool(300, 2000);
    }

    public Object initialValue() {
        return this.func_72341_a();
    }
}
