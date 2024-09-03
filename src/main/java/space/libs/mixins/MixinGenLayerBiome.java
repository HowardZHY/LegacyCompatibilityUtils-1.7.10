package space.libs.mixins;

import net.minecraft.world.gen.layer.GenLayerBiome;
import net.minecraftforge.common.BiomeManager;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

import static net.minecraftforge.common.BiomeManager.BiomeEntry;

@SuppressWarnings("unused")
@Mixin(GenLayerBiome.class)
public class MixinGenLayerBiome {

    public List<BiomeEntry> desertBiomes = BiomeManager.getBiomes(BiomeManager.BiomeType.DESERT);

    public List<BiomeEntry> warmBiomes = BiomeManager.getBiomes(BiomeManager.BiomeType.WARM);

    public List<BiomeEntry> coolBiomes = BiomeManager.getBiomes(BiomeManager.BiomeType.COOL);

    public List<BiomeEntry> icyBiomes = BiomeManager.getBiomes(BiomeManager.BiomeType.ICY);

}
