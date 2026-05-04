package net.minecraft.block;

import net.minecraft.block.material.Material;

/**
 * {@link net.minecraft.block.BlockLiquid }
 * @implNote Will be remapped, Don't use.
 */
public abstract class BlockFluid extends Block {

    protected BlockFluid(Material materialIn) {
        super(materialIn);
    }

    public BlockFluid(int id, Material material) {
        this(material);
        IBlock.InitLegacyBlock(this, id, "BlockFluid");
    }
}
