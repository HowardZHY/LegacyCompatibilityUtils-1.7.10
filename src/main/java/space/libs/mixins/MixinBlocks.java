package space.libs.mixins;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import space.libs.util.cursedmixinextensions.annotations.Public;

@SuppressWarnings("all")
@Mixin(Blocks.class)
public class MixinBlocks {

    @Public
    private static @Final Block field_150354_m = (Block) Block.blockRegistry.getObject("sand");

}
