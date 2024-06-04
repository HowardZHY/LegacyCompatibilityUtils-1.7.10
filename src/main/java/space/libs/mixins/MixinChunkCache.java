package space.libs.mixins;

import net.minecraft.util.Vec3Pool;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.interfaces.IBlockAccessVec3;

@Mixin(ChunkCache.class)
public abstract class MixinChunkCache implements IBlockAccessVec3 {

    @Shadow
    private World worldObj;

    public Vec3Pool func_82732_R() {
        IBlockAccessVec3 accessor = (IBlockAccessVec3) this.worldObj;
        return accessor.func_82732_R();
    }
}
