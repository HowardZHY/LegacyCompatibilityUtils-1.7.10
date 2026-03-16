package space.libs.mixins.world;

import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenMinable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.libs.util.cursedmixinextensions.annotations.NewConstructor;
import space.libs.util.cursedmixinextensions.annotations.ShadowConstructor;

@SuppressWarnings("unused")
@Mixin(WorldGenMinable.class)
public class MixinWorldGenMineable {

    public int field_76542_a;

    public int field_94523_c;

    @ShadowConstructor
    public void WorldGenMinable(Block block, int p_i45459_2_) {}

    @ShadowConstructor
    public void WorldGenMinable(Block block, int p_i45460_2_, Block target) {}

    @ShadowConstructor
    public void WorldGenMinable(Block block, int meta, int number, Block target) {}

    @NewConstructor
    public void WorldGenMineable(int id, int p_i2020_2_) {
        WorldGenMinable(Block.getBlockById(id), p_i2020_2_);
    }

    @NewConstructor
    public void WorldGenMineable(int id, int p_i2021_2_, int target) {
        WorldGenMinable(Block.getBlockById(id), p_i2021_2_, Block.getBlockById(target));
    }

    @NewConstructor
    public void WorldGenMineable(int id, int meta, int number, int target) {
        WorldGenMinable(Block.getBlockById(id), meta, number, Block.getBlockById(target));
    }

    @Inject(method = "<init>(Lnet/minecraft/block/Block;IILnet/minecraft/block/Block;)V", at = @At("RETURN"))
    public void init(Block block, int meta, int number, Block target, CallbackInfo ci) {
        this.field_76542_a = Block.getIdFromBlock(block);
        this.field_94523_c = Block.getIdFromBlock(target);
    }
}
