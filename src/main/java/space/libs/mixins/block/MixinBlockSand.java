package space.libs.mixins.block;

import net.minecraft.block.BlockSand;
import net.minecraft.block.IBlock;
import net.minecraft.block.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import space.libs.util.cursedmixinextensions.annotations.*;

@SuppressWarnings("unused")
@Mixin(BlockSand.class)
public abstract class MixinBlockSand extends MixinBlockFalling {

    @Public
    private static boolean field_72192_a;

    @ShadowSuperConstructor
    public void BlockFalling() {}

    @ShadowSuperConstructor
    public void BlockFalling(Material material) {}

    @NewConstructor
    public void BlockSand(int id) {
        BlockFalling();
        IBlock.InitLegacyBlock(GetBlockInstance(), id, "BlockSand");
    }

    @NewConstructor
    public void BlockSand(int id, Material material) {
        BlockFalling(material);
        IBlock.InitLegacyBlock(GetBlockInstance(), id, "BlockSand");
    }
}
