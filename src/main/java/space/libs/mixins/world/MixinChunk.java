package space.libs.mixins.world;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import space.libs.util.cursedmixinextensions.annotations.NewConstructor;
import space.libs.util.cursedmixinextensions.annotations.ShadowConstructor;

@SuppressWarnings("unused")
@Mixin(Chunk.class)
public class MixinChunk {

    @ShadowConstructor
    public void Chunk(World world, Block[] blocks, int x, int z) {}

    @ShadowConstructor
    public void Chunk(World world, Block[] blocks, byte[] metadata, int x, int z) {}

    @NewConstructor
    public void Chunk(World world, byte[] ids, int x, int z) {
        Chunk(world, Convert(ids), x, z);
    }

    @NewConstructor
    public void Chunk(World world, byte[] ids, byte[] metadata, int x, int z) {
        Chunk(world, Convert(ids), metadata, x, z);
    }

    private static Block[] Convert(byte[] ids) {
        Block[] blocks = new Block[ids.length];
        for (int i = 0; i < ids.length; i++) {
            blocks[i] = Block.getBlockById(ids[i] & 255);
        }
        return blocks;
    }

}
