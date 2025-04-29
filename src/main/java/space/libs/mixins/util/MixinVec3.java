package space.libs.mixins.util;

import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3Pool;
import org.spongepowered.asm.mixin.Mixin;
import space.libs.util.cursedmixinextensions.annotations.NewConstructor;
import space.libs.util.cursedmixinextensions.annotations.Public;
import space.libs.util.cursedmixinextensions.annotations.ShadowConstructor;

@SuppressWarnings("all")
@Mixin(Vec3.class)
public class MixinVec3 {

    @Public
    private static Vec3Pool field_82592_a = new Vec3Pool(-1, -1);

    public Vec3Pool field_72447_d;

    @ShadowConstructor
    protected void Vec3(double x, double y, double z) {}

    @NewConstructor
    public void Vec3(Vec3Pool pool, double x, double y, double z) {
        this.Vec3(x, y, z);
        this.field_72447_d = pool;
    }

}
