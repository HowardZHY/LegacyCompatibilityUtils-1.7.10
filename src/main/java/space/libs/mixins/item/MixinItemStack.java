package space.libs.mixins.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.libs.util.MappedName;
import space.libs.util.cursedmixinextensions.annotations.NewConstructor;
import space.libs.util.cursedmixinextensions.annotations.ShadowConstructor;

@SuppressWarnings("unused")
@Mixin(ItemStack.class)
public abstract class MixinItemStack {

    @ShadowConstructor
    public void ItemStack(Item p_i1881_1_, int p_i1881_2_, int p_i1881_3_) {}

    @NewConstructor
    public void ItemStack(int p_i1882_1_, int p_i1882_2_, int p_i1882_3_) {
        this.ItemStack(Item.getItemById(p_i1882_1_), p_i1882_2_, p_i1882_3_);
    }

    @MappedName(value = "itemID", until = "1.6.4")
    public int field_77993_c;

    @Inject(method = "setItem", at = @At("RETURN"))
    public void setItem(Item item, CallbackInfo ci) {
        this.field_77993_c = Item.getIdFromItem(item);
    }
}
