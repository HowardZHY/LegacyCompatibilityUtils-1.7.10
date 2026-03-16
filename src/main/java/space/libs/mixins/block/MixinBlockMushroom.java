package space.libs.mixins.block;

import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockMushroom;
import org.spongepowered.asm.mixin.Mixin;
import space.libs.util.cursedmixinextensions.annotations.ChangeSuperClass;

@Mixin(BlockMushroom.class)
@ChangeSuperClass(BlockFlower.class)
public abstract class MixinBlockMushroom extends MixinBlock {

    @Override
    public int damageDropped(int meta) {
        return 0;
    }
}
