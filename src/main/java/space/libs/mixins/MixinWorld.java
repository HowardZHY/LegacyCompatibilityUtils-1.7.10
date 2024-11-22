package space.libs.mixins;

import net.minecraft.block.Block;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import net.minecraft.world.IBlockAccessBridge;
import org.spongepowered.asm.mixin.Shadow;

@SuppressWarnings("all")
@Mixin(World.class)
public abstract class MixinWorld implements IBlockAccessBridge {

    /** difficultySetting */
    public int field_73013_u = 2; //TODO?

    /** vecPool */
    @Mutable
    public final Vec3Pool field_82741_K = new Vec3Pool(300, 2000);

    @Shadow
    @Override
    public abstract Block getBlock(int p_147439_1_, int p_147439_2_, int p_147439_3_);

    @Override
    public int func_72798_a(int x, int y, int z) {
        return Block.getIdFromBlock(this.getBlock(x, y, z));
    }

    /** getWorldVec3Pool */
    public Vec3Pool func_82732_R() {
        return this.field_82741_K;
    }

}
