package space.libs.mixins.block;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Block.class, priority = 4060)
public abstract class MixinBlockLegacyMethods {

    @Shadow
    public abstract Block setUnlocalizedName(String name);

    @Shadow
    public void breakBlock(World worldIn, int x, int y, int z, Block blockBroken, int meta) {}

    private ThreadLocal<Boolean> legacyBreakBlock = ThreadLocal.withInitial(() -> false);

    @Inject(method = "breakBlock", at = @At("HEAD"), cancellable = true)
    public void breakBlock(World worldIn, int x, int y, int z, Block blockBroken, int meta, CallbackInfo ci) {
        if (legacyBreakBlock.get()) {
            this.legacyBreakBlock.set(false);
        } else {
            this.func_71852_a(worldIn, x, y, z, Block.getIdFromBlock(blockBroken), meta);
            ci.cancel();
        }
    }

    public void func_71852_a(World worldIn, int x, int y, int z, int block, int meta) {
        this.legacyBreakBlock.set(true);
        this.breakBlock(worldIn, x, y, z, Block.getBlockById(block), meta);
    }

    public Block func_71864_b(String name) {
        return this.setUnlocalizedName(name);
    }
}
