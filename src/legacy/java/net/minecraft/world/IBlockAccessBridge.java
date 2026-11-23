package net.minecraft.world;

import net.minecraft.block.Block;
import net.minecraft.util.Vec3Pool;
import net.minecraftforge.common.util.ForgeDirection;

@SuppressWarnings("unused")
public interface IBlockAccessBridge {

    Block getBlock(int x, int y, int z);

    boolean isSideSolid(int x, int y, int z, ForgeDirection side, boolean _default);

    default int func_72798_a(int x, int y, int z) {
        return Block.getIdFromBlock(getBlock(x, y, z));
    }

    Vec3Pool func_82732_R();

    default boolean isBlockSolidOnSide(int x, int y, int z, ForgeDirection side, boolean _default) {
        return this.isSideSolid(x, y, z, side, _default);
    }
}
