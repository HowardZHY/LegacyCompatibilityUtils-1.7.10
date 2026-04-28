package space.libs.mixins.interfaces;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.ITileEntityProviderBridge;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = ITileEntityProvider.class, priority = 10)
public interface MixinITileEntityProvider extends ITileEntityProviderBridge {

    @Shadow
    @Override
    TileEntity createNewTileEntity(World worldIn, int meta);

    @Override
    TileEntity func_72274_a(World worldIn);

}
