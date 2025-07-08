package space.libs.mixins.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("unused")
@Mixin(value = GuiContainer.class, priority = 500)
public abstract class MixinGuiContainer extends MixinGuiScreen {

    @Shadow
    protected int xSize;

    @Shadow
    protected int ySize;

    public int field_74194_b = 176;

    public int field_74195_c = 166;

    public void func_74185_a(float partialTicks, int mouseX, int mouseY) {}

    public void func_74189_g(int mouseX, int mouseY) {}

    /**
     * @author HowardZHY
     * @reason Legacy Impl
     */
    @SuppressWarnings("OverwriteModifiers")
    @Overwrite
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.func_74185_a(partialTicks, mouseX, mouseY);
    }

    @Inject(method = "initGui", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiScreen;initGui()V", shift = At.Shift.AFTER))
    public void initGui(CallbackInfo ci) {
        if (!this.getClass().getName().startsWith("net.minecraft")) {
            if (xSize != field_74194_b || ySize != field_74195_c) {
                xSize = field_74194_b;
                ySize = field_74195_c;
            }
        }
    }

    @Inject(method = "drawGuiContainerForegroundLayer", at = @At("RETURN"))
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY, CallbackInfo ci) {
        this.func_74189_g(mouseX, mouseY);
    }

}
