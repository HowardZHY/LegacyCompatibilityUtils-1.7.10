package space.libs.mixins.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockFluid;
import net.minecraft.block.IBlock;
import net.minecraft.init.Blocks;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.libs.util.cursedmixinextensions.annotations.Public;

@Mixin(value = Block.class, priority = 4050)
public abstract class MixinBlockLegacyTypedFields {

    @Public private static BlockFluid field_71942_A;
    @Public private static BlockFluid field_71944_C;

    @Public private static BlockFlower field_72103_ag;
    @Public private static BlockFlower field_72109_af;

    @Dynamic
    @Inject(method = "registerBlocks", at = @At("RETURN"))
    private static void registerBlocks(CallbackInfo ci) {
        field_71942_A = IBlock.GetLegacyFluid(Blocks.flowing_water);
        field_71944_C = IBlock.GetLegacyFluid(Blocks.flowing_lava);
        field_72103_ag = IBlock.GetLegacyFlower(Blocks.red_mushroom);
        field_72109_af = IBlock.GetLegacyFlower(Blocks.brown_mushroom);
    }
}
