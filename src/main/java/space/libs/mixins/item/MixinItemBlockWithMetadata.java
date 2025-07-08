package space.libs.mixins.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.libs.util.cursedmixinextensions.annotations.NewConstructor;
import space.libs.util.cursedmixinextensions.annotations.ShadowSuperConstructor;

@Mixin(ItemBlockWithMetadata.class)
public abstract class MixinItemBlockWithMetadata extends MixinItemBlock {

    @Shadow
    private Block field_150950_b;

    public Block field_96601_a;

    @ShadowSuperConstructor
    public void ItemBlock(int id) {}

    @NewConstructor
    public void ItemBlockWithMetadata(int id, Block blockIn) {
        ItemBlock(id);
        this.field_96601_a = blockIn;
        this.field_150950_b = blockIn;
        this.setMaxDurability(0);
        this.setHasSubtypes(true);
    }

    @Inject(method = "<init>(Lnet/minecraft/block/Block;Lnet/minecraft/block/Block;)V", at = @At("RETURN"))
    public void init(Block block, Block blockIn, CallbackInfo ci) {
        this.field_96601_a = blockIn;
    }
}
