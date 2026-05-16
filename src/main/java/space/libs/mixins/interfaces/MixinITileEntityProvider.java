package space.libs.mixins.interfaces;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.ITileEntityProviderBridge;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = ITileEntityProvider.class, priority = 10)
public interface MixinITileEntityProvider extends ITileEntityProviderBridge {

    /**
     * @author HowardZHY
     * @reason Legacy Impl
     */
    @SuppressWarnings("OverwriteModifiers")
    @Overwrite
    @Override
    default TileEntity createNewTileEntity(World worldIn, int meta) {
        return func_72274_a(worldIn);
    }

    @Override
    default TileEntity func_72274_a(World worldIn) {
        try {
            return createNewTileEntity(worldIn, 0);
        } catch (StackOverflowError ignored) {
            throw new AbstractMethodError("Bad TileEntity Impl: ".concat(this.getClass().getName()));
        }
    }

}
