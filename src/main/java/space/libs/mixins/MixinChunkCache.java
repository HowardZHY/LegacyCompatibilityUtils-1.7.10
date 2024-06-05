package space.libs.mixins;

import net.minecraft.util.Vec3Pool;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.world.IBlockAccessVec3Pool;

@Mixin(ChunkCache.class)
public abstract class MixinChunkCache implements IBlockAccessVec3Pool {

    @Shadow
    private World worldObj;

    public Vec3Pool func_82732_R() {
        IBlockAccessVec3Pool accessor = (IBlockAccessVec3Pool) this.worldObj;
        return accessor.func_82732_R();
    }
}
