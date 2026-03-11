package space.libs.mixins.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import space.libs.util.cursedmixinextensions.annotations.NewConstructor;
import space.libs.util.cursedmixinextensions.annotations.ShadowConstructor;
import space.libs.util.forge.RegistryUtils;

@SuppressWarnings("unused")
@Mixin(BlockContainer.class)
public abstract class MixinBlockContainer extends MixinBlock {

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
}
