package space.libs.mixins.block;

import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import space.libs.util.cursedmixinextensions.annotations.NewConstructor;
import space.libs.util.cursedmixinextensions.annotations.ShadowSuperConstructor;

@Mixin(BlockStaticLiquid.class)
public abstract class MixinBlockStaticLiquid extends MixinBlockLiquid {

    @ShadowSuperConstructor
    public void BlockLiquid(int id, Material material) {}

    @NewConstructor
    public void BlockStaticLiquid(int id, Material material) {
        BlockLiquid(id, material);
    }
}
