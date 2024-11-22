package space.libs.mixins;

import net.minecraft.block.Block;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.world.IBlockAccessBridge;

@Mixin(ChunkCache.class)
public abstract class MixinChunkCache implements IBlockAccessBridge {

    @Shadow
    private World worldObj;

    @Shadow
    public abstract Block getBlock(int x, int y, int z);

    @Override
    public int func_72798_a(int x, int y, int z) {
        return Block.getIdFromBlock(this.getBlock(x, y, z));
    }

    public Vec3Pool func_82732_R() {
        IBlockAccessBridge accessor = (IBlockAccessBridge) this.worldObj;
        return accessor.func_82732_R();
    }
}
