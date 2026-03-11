package net.minecraft.block;

import net.minecraft.block.material.Material;

/**
 * @implNote Will be remapped, Don't use.
 */
public class BlockFluid extends Block {

    protected BlockFluid(Material materialIn) {
        super(materialIn);
    }

    public BlockFluid(int id, Material material) {
        this(material);
        IBlock.InitLegacyBlock(this, id, "BlockFluid");
    }
}
