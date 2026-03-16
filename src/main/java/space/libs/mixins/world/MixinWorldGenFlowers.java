package space.libs.mixins.world;

import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.libs.util.cursedmixinextensions.annotations.NewConstructor;
import space.libs.util.cursedmixinextensions.annotations.ShadowConstructor;

@SuppressWarnings("unused")
@Mixin(WorldGenFlowers.class)
public class MixinWorldGenFlowers {

    public int field_76528_a;

    @ShadowConstructor
    public void WorldGenFlowers(Block block) {}

    @NewConstructor
    public void WorldGenFlowers(int id) {
        WorldGenFlowers(Block.getBlockById(id));
    }

    @Inject(method = "<init>(Lnet/minecraft/block/Block;)V", at = @At("RETURN"))
    public void init(Block block, CallbackInfo ci) {
        this.field_76528_a = Block.getIdFromBlock(block);
    }
}
