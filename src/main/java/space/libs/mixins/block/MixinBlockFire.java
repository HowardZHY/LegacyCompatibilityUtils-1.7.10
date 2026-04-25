package space.libs.mixins.block;

import net.minecraft.block.BlockFire;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockFire.class)
public abstract class MixinBlockFire extends MixinBlock {

    @Override
    public void func_71928_r_() {}

}
