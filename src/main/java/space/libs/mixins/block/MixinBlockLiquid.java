package space.libs.mixins.block;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import space.libs.util.cursedmixinextensions.annotations.*;


/**
 * @see net.minecraft.block.BlockFluid
 */
@SuppressWarnings("unused")
@Mixin(BlockLiquid.class)
public abstract class MixinBlockLiquid extends MixinBlock {

    @ShadowSuperConstructor
    protected void BlockFluid(int id, Material material) {}

    @NewConstructor
    public void BlockLiquid(int id, Material material) {
        BlockFluid(id, material);
    }
}
