package space.libs.mixins.block;

import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import space.libs.util.cursedmixinextensions.annotations.NewConstructor;
import space.libs.util.cursedmixinextensions.annotations.ShadowSuperConstructor;

@Mixin(BlockDynamicLiquid.class)
public abstract class MixinBlockDynamicLiquid extends MixinBlockLiquid {

    @ShadowSuperConstructor
    public void BlockLiquid(int id, Material material) {}

    @NewConstructor
    public void BlockDynamicLiquid(int id, Material material) {
        BlockLiquid(id, material);
    }
}
