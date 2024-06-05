package space.libs.mixins.interfaces;

import net.minecraft.util.Vec3Pool;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.world.IBlockAccessVec3Pool;

@Mixin(IBlockAccess.class)
public interface MixinIBlockAccess extends IBlockAccessVec3Pool {
    Vec3Pool func_82732_R();
}
