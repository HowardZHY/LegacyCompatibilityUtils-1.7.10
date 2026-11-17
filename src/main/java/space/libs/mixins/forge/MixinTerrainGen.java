package space.libs.mixins.forge;

import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraftforge.event.terraingen.TerrainGen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.util.cursedmixinextensions.annotations.Public;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mixin(value = TerrainGen.class, remap = false)
public abstract class MixinTerrainGen {

    @Shadow(prefix = "original$")
    public static NoiseGenerator[] original$getModdedNoiseGenerators(World world, Random rand, NoiseGenerator[] original) {
        throw new AbstractMethodError();
    }

    @Public
    private static NoiseGeneratorOctaves[] getModdedNoiseGenerators(World world, Random rand, NoiseGeneratorOctaves[] original) {
        NoiseGenerator[] generators = original$getModdedNoiseGenerators(world, rand, original);
        List<NoiseGeneratorOctaves> octaves = new ArrayList<>();
        for (NoiseGenerator generator : generators) {
            if (generator instanceof NoiseGeneratorOctaves) {
                octaves.add((NoiseGeneratorOctaves) generator);
            }
        }
        return octaves.toArray(new NoiseGeneratorOctaves[0]);
    }
}
