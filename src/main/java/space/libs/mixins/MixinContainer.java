package space.libs.mixins;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Container.class, priority = 6600)
public abstract class MixinContainer {

    private short count;

    private boolean unsafe;

    @Shadow
    public abstract ItemStack slotClick(int slotId, int clickedButton, int mode, EntityPlayer player);

    @Inject(method = "transferStackInSlot", at = @At("HEAD"))
    public void transferStackInSlot(EntityPlayer player, int index, CallbackInfoReturnable<ItemStack> cir) {
        this.unsafe = true;
    }

    @Inject(method = "retrySlotClick", at = @At("HEAD"), cancellable = true)
    public void retrySlotClick(int slot, int button, boolean unused, EntityPlayer player, CallbackInfo ci) {
        this.count++;
        if (unsafe && this.count > 128) {
            this.unsafe = false;
            this.count = 0;
            this.slotClick(slot, button, 0, player);
            ci.cancel();
        }
    }
}
