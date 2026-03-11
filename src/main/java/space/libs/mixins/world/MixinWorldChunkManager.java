package space.libs.mixins.world;

import net.minecraft.world.ChunkPosition;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Random;

@Mixin(value = WorldChunkManager.class, priority = 4040)
public abstract class MixinWorldChunkManager {

    private ThreadLocal<Boolean> Activated = ThreadLocal.withInitial(() -> false);

    @Shadow
    public abstract ChunkPosition findBiomePosition(int x, int y, int z, List<BiomeGenBase> list, Random random);

    public ChunkPosition func_76941_a(int x, int y, int z, List<BiomeGenBase> list, Random random) {
        this.Activated.set(true);
        return this.findBiomePosition(x, y, z, list, random);
    }

    @Inject(method = "findBiomePosition", at = @At("HEAD"), cancellable = true)
    public void findBiomePosition(int x, int y, int z, List<BiomeGenBase> list, Random random, CallbackInfoReturnable<ChunkPosition> cir) {
        if (Activated.get()) {
            this.Activated.set(false);
        } else {
            ChunkPosition position = this.func_76941_a(x, y, z, list, random);
            cir.setReturnValue(position);
        }
    }
}
