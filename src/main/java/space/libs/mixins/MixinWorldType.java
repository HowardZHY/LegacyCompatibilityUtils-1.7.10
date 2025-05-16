package space.libs.mixins;

import com.google.common.collect.ObjectArrays;
import com.google.common.collect.Sets;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.libs.interfaces.IWorldType;
import space.libs.util.BiomeUtils;
import space.libs.util.cursedmixinextensions.annotations.Public;

import java.util.*;

@SuppressWarnings("unused")
@Mixin(WorldType.class)
public class MixinWorldType implements IWorldType {

    @Shadow
    private @Final int worldTypeId;

    @Public @SuppressWarnings("all")
    private static BiomeGenBase[] base11Biomes = new BiomeGenBase[] { BiomeGenBase.desert, BiomeGenBase.forest, BiomeGenBase.extremeHills, BiomeGenBase.swampland, BiomeGenBase.plains, BiomeGenBase.taiga };

    @Public @SuppressWarnings("all")
    private static BiomeGenBase[] base12Biomes = (BiomeGenBase[]) ObjectArrays.concat((Object[])base11Biomes, BiomeGenBase.jungle);

    public BiomeGenBase[] biomesForWorldType;

    @Inject(method = "<init>(ILjava/lang/String;I)V", at = @At("RETURN"))
    public void init(int id, String name, int version, CallbackInfo ci) {
        setBiomesForWorldType();
    }

    public BiomeGenBase[] getBiomesForWorldType() {
        return this.biomesForWorldType;
    }

    public void addNewBiome(BiomeGenBase biome) {
        if (this.biomesForWorldType == null) {
            setBiomesForWorldType();
        }
        Set<BiomeGenBase> newBiomesForWorld = Sets.newLinkedHashSet(Arrays.asList(this.biomesForWorldType));
        newBiomesForWorld.add(biome);
        this.biomesForWorldType = newBiomesForWorld.toArray(new BiomeGenBase[0]);
        BiomeDictionary.registerBiomeType(biome, BiomeDictionary.Type.FOREST);
        BiomeManager.addSpawnBiome(biome);
    }

    public void removeBiome(BiomeGenBase biome) {
        if (this.biomesForWorldType == null) {
            setBiomesForWorldType();
        }
        Set<BiomeGenBase> newBiomesForWorld = Sets.newLinkedHashSet(Arrays.asList(this.biomesForWorldType));
        newBiomesForWorld.remove(biome);
        this.biomesForWorldType = newBiomesForWorld.toArray(new BiomeGenBase[0]);
        List<BiomeManager.BiomeEntry>[] lists = BiomeUtils.getForgeBiomes();
        for (List<BiomeManager.BiomeEntry> biomes : lists) {
            biomes.removeIf(entry -> entry.biome == biome);
        }
        BiomeManager.removeSpawnBiome(biome);
    }

    public void setBiomesForWorldType() {
        this.biomesForWorldType = base12Biomes;
        if (this.worldTypeId == 8) {
            this.biomesForWorldType = base11Biomes;
        }
    }
}
