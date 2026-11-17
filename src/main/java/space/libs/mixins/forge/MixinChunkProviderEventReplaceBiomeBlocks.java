package space.libs.mixins.forge;

import net.minecraft.block.Block;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import org.spongepowered.asm.mixin.Mixin;
import space.libs.util.cursedmixinextensions.annotations.NewConstructor;
import space.libs.util.cursedmixinextensions.annotations.ShadowConstructor;

@Mixin(value = ChunkProviderEvent.ReplaceBiomeBlocks.class, remap = false)
public class MixinChunkProviderEventReplaceBiomeBlocks {

    public byte[] blockArray;

    @ShadowConstructor
    public void ReplaceBiomeBlocks(IChunkProvider chunkProvider, int chunkX, int chunkZ, Block[] blockArray, BiomeGenBase[] biomeArray) {}

    @NewConstructor
    public void ReplaceBiomeBlocks(IChunkProvider chunkProvider, int chunkX, int chunkZ, byte[] blockArray, BiomeGenBase[] biomeArray) {
        this.ReplaceBiomeBlocks(chunkProvider, chunkX, chunkZ, transformBlockArray(blockArray), biomeArray);
        this.blockArray = blockArray;
    }

    private static Block[] transformBlockArray(byte[] blockArray) {
        if (blockArray == null) {
            return new Block[0];
        }
        Block[] blocks = new Block[blockArray.length];
        for (int i = 0; i < blockArray.length; i++) {
            blocks[i] = Block.getBlockById(blockArray[i] & 255);
        }
        return blocks;
    }
}
