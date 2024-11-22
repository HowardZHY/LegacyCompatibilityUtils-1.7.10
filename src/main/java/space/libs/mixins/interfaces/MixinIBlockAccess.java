package space.libs.mixins.interfaces;

import net.minecraft.block.Block;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.world.IBlockAccessBridge;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(IBlockAccess.class)
public interface MixinIBlockAccess extends IBlockAccessBridge {

    @Shadow
    Block getBlock(int x, int y, int z);

    default int func_72798_a(int x, int y, int z) {
        return Block.getIdFromBlock(getBlock(x, y, z));
    }

    Vec3Pool func_82732_R();
}
