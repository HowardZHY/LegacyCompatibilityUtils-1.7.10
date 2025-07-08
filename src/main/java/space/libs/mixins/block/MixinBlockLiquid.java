package space.libs.mixins.block;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import space.libs.util.cursedmixinextensions.annotations.NewConstructor;
import space.libs.util.cursedmixinextensions.annotations.ShadowConstructor;
import space.libs.util.forge.RegistryUtils;

@SuppressWarnings("unused")
@Mixin(BlockLiquid.class)
public abstract class MixinBlockLiquid extends MixinBlock {

    @ShadowConstructor
    protected void BlockLiquid(Material material) {}

    @NewConstructor
    public void BlockLiquid(int id, Material material) {
        BlockLiquid(material);
        if (id <= 0) {
            this.SetLegacyBlockNoID("BlockFluid");
            return;
        }
        this.SetLegacyBlock(id, "BlockFluid");
        RegistryUtils.putBlock(GetBlockInstance(), this.field_71990_ca);
    }
}
