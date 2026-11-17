package space.libs.mixins.world;

import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenLakes;
import org.spongepowered.asm.mixin.Mixin;
import space.libs.util.cursedmixinextensions.annotations.NewConstructor;
import space.libs.util.cursedmixinextensions.annotations.ShadowConstructor;

@Mixin(WorldGenLakes.class)
public abstract class MixinWorldGenLakes {

    @ShadowConstructor
    public void WorldGenLakes(Block block) {}

    @NewConstructor
    public void WorldGenLakes(int id) {
        this.WorldGenLakes(Block.getBlockById(id));
    }
}
