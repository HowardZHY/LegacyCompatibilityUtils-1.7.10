package space.libs.mixins;

import net.minecraft.block.BlockFire;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockFire.class)
public abstract class MixinBlockFire extends MixinBlock {

    @Shadow
    @Override
    public abstract boolean canPlaceBlockAt(World worldIn, int x, int y, int z);

    @Override
    public boolean func_71930_b(World worldIn, int x, int y, int z) {
        return this.canPlaceBlockAt(worldIn, x, y, z);
    }
}
