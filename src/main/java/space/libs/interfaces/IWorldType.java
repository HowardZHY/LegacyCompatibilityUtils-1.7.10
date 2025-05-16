package space.libs.interfaces;

import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;

public interface IWorldType {

    IWorldType DEFAULT = (IWorldType) WorldType.DEFAULT;

    void addNewBiome(BiomeGenBase biome);

    void removeBiome(BiomeGenBase biome);

}
