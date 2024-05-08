package space.libs.mixins;

import net.minecraft.util.AABBPool;
import net.minecraft.util.AxisAlignedBB;
import org.spongepowered.asm.mixin.Mixin;
import space.libs.util.cursedmixinextensions.annotations.Public;

@SuppressWarnings("unused")
@Mixin(AxisAlignedBB.class)
public class MixinAxisAlignedBB {

    /*** getAABBPool */
    @Public
    private static AABBPool func_72332_a() {
        return new AABBPool(300, 2000);
    }

}
