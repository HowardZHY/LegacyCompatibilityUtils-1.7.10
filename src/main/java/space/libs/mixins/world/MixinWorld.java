package space.libs.mixins.world;

import net.minecraft.block.Block;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import org.spongepowered.asm.mixin.*;
import net.minecraft.world.IBlockAccessBridge;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.util.MappedName;

@SuppressWarnings("unused")
@Mixin(World.class)
public abstract class MixinWorld implements IBlockAccessBridge {

    @MappedName("difficultySetting")
    public int field_73013_u = 2; //TODO?

    @MappedName("vecPool")
    @Mutable
    public final Vec3Pool field_82741_K = new Vec3Pool(300, 2000);

    @Shadow
    @Override
    public abstract Block getBlock(int x, int y, int z);

    @Shadow
    public abstract boolean setBlock(int x, int y, int z, Block blockIn, int metadataIn, int flags);

    @Shadow
    public abstract boolean setBlock(int x, int y, int z, Block blockType);

    @Shadow
    public abstract void notifyBlockChange(int x, int y, int z, Block block);

    @Shadow
    public abstract void notifyBlocksOfNeighborChange(int x, int y, int z, Block block);

    @Shadow
    public abstract void notifyBlocksOfNeighborChange(int x, int y, int z, Block block, int par5);

    @Shadow
    public abstract void notifyBlockOfNeighborChange(int x, int y, int z, Block block);

    @Shadow
    public abstract boolean isBlockTickScheduledThisTick(int x, int y, int z, Block block);

    @Shadow
    public abstract MovingObjectPosition rayTraceBlocks(Vec3 pos, Vec3 pos1, boolean b, boolean b1, boolean b2);

    @Shadow
    public abstract void addBlockEvent(int x, int y, int z, Block blockIn, int eventId, int eventParameter);

    @Shadow
    public abstract void updateNeighborsAboutBlockChange(int x, int y, int z, Block block);

    @Shadow(remap = false)
    public abstract boolean isSideSolid(int x, int y, int z, ForgeDirection side);

    @Shadow(remap = false)
    @Override
    public abstract boolean isSideSolid(int x, int y, int z, ForgeDirection side, boolean _default);

    @Override
    public int func_72798_a(int x, int y, int z) {
        return Block.getIdFromBlock(this.getBlock(x, y, z));
    }

    public void func_72821_m(int x, int y, int z, int block) {
        this.notifyBlockOfNeighborChange(x, y, z, Block.getBlockById(block));
    }

    public MovingObjectPosition func_72831_a(Vec3 pos, Vec3 pos1, boolean b, boolean b1) {
        return this.rayTraceBlocks(pos, pos1, b, b1, false);
    }

    public boolean func_72832_d(int x, int y, int z, int block, int metadataIn, int flags) {
        return this.setBlock(x, y, z, Block.getBlockById(block), metadataIn, flags);
    }

    public void func_72851_f(int x, int y, int z, int block) {
        this.notifyBlockChange(x, y, z, Block.getBlockById(block));
    }

    public void func_72898_h(int x, int y, int z, int block) {
        this.notifyBlocksOfNeighborChange(x, y, z, Block.getBlockById(block));
    }

    public void func_72965_b(int x, int y, int z, int block, int eventId, int eventParameter) {
        this.addBlockEvent(x, y, z, Block.getBlockById(block), eventId, eventParameter);
    }

    @Override
    @MappedName("getWorldVec3Pool")
    public Vec3Pool func_82732_R() {
        return this.field_82741_K;
    }

    public boolean func_94573_a(int x, int y, int z, int block) {
        return this.isBlockTickScheduledThisTick(x, y, z, Block.getBlockById(block));
    }

    public boolean func_94575_c(int x, int y, int z, int block) {
        return this.setBlock(x, y, z, Block.getBlockById(block));
    }

    public void func_96439_d(int x, int y, int z, int block, int par5) {
        this.notifyBlocksOfNeighborChange(x, y, z, Block.getBlockById(block), par5);
    }

    public void func_96440_m(int x, int y, int z, int block) {
        this.updateNeighborsAboutBlockChange(x, y, z, Block.getBlockById(block));
    }

    public boolean isBlockSolidOnSide(int x, int y, int z, ForgeDirection side) {
        return this.isSideSolid(x, y, z, side);
    }

    @Override
    public boolean isBlockSolidOnSide(int x, int y, int z, ForgeDirection side, boolean _default) {
        return this.isSideSolid(x, y, z, side, _default);
    }
}
