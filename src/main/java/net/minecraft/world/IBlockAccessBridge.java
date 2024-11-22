package net.minecraft.world;

import net.minecraft.block.Block;
import net.minecraft.util.Vec3Pool;

@SuppressWarnings("unused")
public interface IBlockAccessBridge {

    Block getBlock(int x, int y, int z);

    default int func_72798_a(int x, int y, int z) {
        return Block.getIdFromBlock(getBlock(x, y, z));
    }

    Vec3Pool func_82732_R();
}
