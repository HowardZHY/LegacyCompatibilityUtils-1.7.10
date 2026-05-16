package space.libs.mixins.world;

import net.minecraft.block.Block;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import org.spongepowered.asm.mixin.*;
import net.minecraft.world.IBlockAccessBridge;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.util.MappedName;

@Mixin(World.class)
public abstract class MixinWorld implements IBlockAccessBridge {

    @MappedName("difficultySetting")
    public int field_73013_u = 2; //TODO?

    @MappedName("vecPool")
    @Mutable
    public final Vec3Pool field_82741_K = new Vec3Pool(300, 2000);

    @Shadow
    @Override
    public abstract Block getBlock(int p_147439_1_, int p_147439_2_, int p_147439_3_);

    @Shadow
    public abstract boolean setBlock(int x, int y, int z, Block blockIn, int metadataIn, int flags);

    @Shadow
    public abstract boolean setBlock(int x, int y, int z, Block blockType);

    @Shadow
    public abstract boolean isSideSolid(int x, int y, int z, ForgeDirection side, boolean _default);

    @Shadow
    public abstract void notifyBlockOfNeighborChange(int x, int y, int z, Block block);

    @Override
    public int func_72798_a(int x, int y, int z) {
        return Block.getIdFromBlock(this.getBlock(x, y, z));
    }

    public boolean func_72832_d(int x, int y, int z, int block, int metadataIn, int flags) {
        return this.setBlock(x, y, z, Block.getBlockById(block), metadataIn, flags);
    }

    @MappedName("getWorldVec3Pool")
    public Vec3Pool func_82732_R() {
        return this.field_82741_K;
    }

    public boolean func_94575_c(int x, int y, int z, int block) {
        return this.setBlock(x, y, z, Block.getBlockById(block));
    }

    public void func_96440_m(int x, int y, int z, int block) {
        this.notifyBlockOfNeighborChange(x, y, z, Block.getBlockById(block));
    }
}
