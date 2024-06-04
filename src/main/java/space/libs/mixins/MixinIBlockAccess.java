package space.libs.mixins;

import net.minecraft.util.Vec3Pool;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import space.libs.interfaces.IBlockAccessVec3;

@Mixin(IBlockAccess.class)
public interface MixinIBlockAccess extends IBlockAccessVec3 {
    Vec3Pool func_82732_R();
}
