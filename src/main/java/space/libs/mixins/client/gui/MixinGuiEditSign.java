package space.libs.mixins.client.gui;

import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.tileentity.TileEntitySign;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiEditSign.class)
public class MixinGuiEditSign {

    public String field_146850_a;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void GuiEditSign(TileEntitySign sign, CallbackInfo ci) {
        this.field_146850_a = "Edit sign message:";
    }

}
