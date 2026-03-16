package space.libs.mixins.block;

import net.minecraft.block.BlockFalling;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockFalling.class)
public abstract class MixinBlockFalling extends MixinBlock {

}
