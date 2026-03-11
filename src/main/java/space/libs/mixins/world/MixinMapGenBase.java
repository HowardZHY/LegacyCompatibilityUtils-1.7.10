package space.libs.mixins.world;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.MapGenBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@SuppressWarnings("unused")
@Mixin(MapGenBase.class)
public abstract class MixinMapGenBase {

    @Shadow
    protected int range;

    @Shadow
    protected Random rand;

    @Shadow
    protected World worldObj;

    @Shadow
    public void generate(IChunkProvider iChunkProvider, World world, int x, int z, Block[] blocks) {}

    public void func_75036_a(IChunkProvider iChunkProvider, World world, int x, int z, byte[] blocks) {
        int range1 = this.range;
        this.worldObj = world;
        this.rand.setSeed(world.getSeed());
        long l1 = this.rand.nextLong();
        long l2 = this.rand.nextLong();
        for (int i = x - range1; i <= x + range1; i++) {
            for (int j = z - range1; j <= z + range1; j++) {
                long l3 = i * l1;
                long l4 = j * l2;
                this.rand.setSeed(l3 ^ l4 ^ world.getSeed());
                this.func_75037_a(world, i, j, x, z, blocks);
            }
        }
    }

    public void func_75037_a(World world, int x, int z, int x1, int z1, byte[] blocks) {}

}
