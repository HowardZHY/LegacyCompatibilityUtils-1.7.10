package net.minecraft.block;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public interface ITileEntityProviderBridge {

    default TileEntity createNewTileEntity(World worldIn, int meta) {
        return func_72274_a(worldIn);
    }

    default TileEntity func_72274_a(World worldIn) {
        try {
            return createNewTileEntity(worldIn, 0);
        } catch (StackOverflowError ignored) {
            throw new AbstractMethodError("Bad TileEntity Impl: ".concat(this.getClass().getName()));
        }
    }
}
