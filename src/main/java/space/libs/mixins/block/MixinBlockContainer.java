package space.libs.mixins.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProviderBridge;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import space.libs.util.cursedmixinextensions.annotations.NewConstructor;
import space.libs.util.cursedmixinextensions.annotations.ShadowConstructor;
import space.libs.util.forge.RegistryUtils;

@SuppressWarnings("unused")
@Mixin(BlockContainer.class)
public abstract class MixinBlockContainer extends MixinBlock implements ITileEntityProviderBridge {

    @ShadowConstructor
    protected void BlockContainer(Material material) {}

    @NewConstructor
    public void BlockContainer(int id, Material material) {
        BlockContainer(material);
        if (id <= 0) {
            this.SetLegacyBlockNoID("BlockContainer");
            return;
        }
        this.SetLegacyBlock(id, "BlockContainer");
        RegistryUtils.putBlock(GetBlockInstance(), this.field_71990_ca);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return func_72274_a(worldIn);
    }

    @Override
    public TileEntity func_72274_a(World worldIn) {
        try {
            return createNewTileEntity(worldIn, 0);
        } catch (StackOverflowError ignored) {
            throw new AbstractMethodError("Bad TileEntity Impl: ".concat(this.getClass().getName()));
        }
    }
}
