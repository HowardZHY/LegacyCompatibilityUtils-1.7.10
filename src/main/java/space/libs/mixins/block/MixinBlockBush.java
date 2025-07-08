package space.libs.mixins.block;

import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.util.cursedmixinextensions.annotations.NewConstructor;
import space.libs.util.cursedmixinextensions.annotations.ShadowSuperConstructor;

@Mixin(BlockBush.class)
public abstract class MixinBlockBush extends MixinBlock {

    @ShadowSuperConstructor
    public void Block(int id, Material material) {}

    @NewConstructor
    public void BlockBush(int id, Material material) {
        Block(id, material);
        this.setTickRandomly(true);
        float f = 0.2F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 3.0F, 0.5F + f);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    @Shadow
    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
        throw new AbstractMethodError();
    }

    @Shadow
    public int getPlantMetadata(IBlockAccess world, int x, int y, int z) {
        throw new AbstractMethodError();
    }
}
