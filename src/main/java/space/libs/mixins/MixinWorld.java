package space.libs.mixins;

import net.minecraft.util.Vec3Pool;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import net.minecraft.world.IBlockAccessVec3Pool;

@SuppressWarnings("all")
@Mixin(World.class)
public class MixinWorld implements IBlockAccessVec3Pool {

    /** vecPool */
    @Mutable
    public final Vec3Pool field_82741_K = new Vec3Pool(300, 2000);

    /** getWorldVec3Pool */
    public Vec3Pool func_82732_R() {
        return this.field_82741_K;
    }

}
