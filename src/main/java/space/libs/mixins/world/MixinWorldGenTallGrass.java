package space.libs.mixins.world;

import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.libs.util.cursedmixinextensions.annotations.NewConstructor;
import space.libs.util.cursedmixinextensions.annotations.ShadowConstructor;

@SuppressWarnings("unused")
@Mixin(WorldGenTallGrass.class)
public class MixinWorldGenTallGrass {

    public int field_76535_a;

    @ShadowConstructor
    public void WorldGenFlowers(Block block, int meta) {}

    @NewConstructor
    public void WorldGenFlowers(int id, int meta) {
        WorldGenFlowers(Block.getBlockById(id), meta);
    }

    @Inject(method = "<init>(Lnet/minecraft/block/Block;I)V", at = @At("RETURN"))
    public void init(Block block, int meta, CallbackInfo ci) {
        this.field_76535_a = Block.getIdFromBlock(block);
    }
}
