package space.libs.mixins.interfaces;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ITileEntityProvider.class)
public interface MixinITileEntityProvider {

    /**
     * @author HowardZHY
     * @reason 1.6.4
     */
    @SuppressWarnings("OverwriteModifiers")
    @Overwrite
    default TileEntity createNewTileEntity(World worldIn, int meta) {
        return func_72274_a(worldIn);
    }

    default TileEntity func_72274_a(World worldIn) {
        //TODO? throw new AbstractMethodError("Bad TileEntity Impl: ".concat(this.getClass().getName()));
        return createNewTileEntity(worldIn, 0);
    }

}
