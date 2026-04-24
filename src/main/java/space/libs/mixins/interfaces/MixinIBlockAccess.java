package space.libs.mixins.interfaces;

import net.minecraft.block.Block;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IBlockAccessBridge;
import net.minecraftforge.common.util.ForgeDirection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(IBlockAccess.class)
public interface MixinIBlockAccess extends IBlockAccessBridge {

    @Shadow
    Block getBlock(int x, int y, int z);

    @Shadow
    boolean isSideSolid(int i, int j, int k, ForgeDirection forgeDirection, boolean bl);

    default int func_72798_a(int x, int y, int z) {
        return Block.getIdFromBlock(getBlock(x, y, z));
    }

    Vec3Pool func_82732_R();

    default boolean isBlockSolidOnSide(int x, int y, int z, ForgeDirection side, boolean _default) {
        return this.isSideSolid(x, y, z, side, _default);
    }
}
