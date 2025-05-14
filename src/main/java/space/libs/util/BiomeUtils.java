package space.libs.util;

import com.google.common.collect.ObjectArrays;
import net.minecraft.world.biome.BiomeGenBase;

public abstract class BiomeUtils {

    public static final BiomeGenBase[] base11Biomes = new BiomeGenBase[] { BiomeGenBase.desert, BiomeGenBase.forest, BiomeGenBase.extremeHills, BiomeGenBase.swampland, BiomeGenBase.plains, BiomeGenBase.taiga };

    public static final BiomeGenBase[] base12Biomes = (BiomeGenBase[]) ObjectArrays.concat((Object[])base11Biomes, BiomeGenBase.jungle);

}
