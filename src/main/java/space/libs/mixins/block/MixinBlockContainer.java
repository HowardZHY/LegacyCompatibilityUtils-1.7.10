package space.libs.mixins.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.util.cursedmixinextensions.annotations.NewConstructor;
import space.libs.util.cursedmixinextensions.annotations.ShadowConstructor;
import space.libs.util.forge.RegistryUtils;

@SuppressWarnings("unused")
@Mixin(BlockContainer.class)
public abstract class MixinBlockContainer extends MixinBlock {

    @Shadow
    public abstract void onBlockAdded(World worldIn, int x, int y, int z);

    @Shadow
    public abstract void breakBlock(World worldIn, int x, int y, int z, Block blockBroken, int meta);

    @Shadow
    public abstract boolean onBlockEventReceived(World worldIn, int x, int y, int z, int eventId, int eventData);

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

    public void func_71861_g(World worldIn, int x, int y, int z) {
        this.onBlockAdded(worldIn, x, y, z);
    }

    public void func_71852_a(World worldIn, int x, int y, int z, int block, int meta) {
        this.breakBlock(worldIn, x, y, z, Block.getBlockById(block), meta);
    }

    public boolean func_71883_b(World worldIn, int x, int y, int z, int eventId, int eventData) {
        return this.onBlockEventReceived(worldIn, x, y, z, eventId, eventData);
    }
}
