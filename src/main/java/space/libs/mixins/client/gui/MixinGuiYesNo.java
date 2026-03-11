package space.libs.mixins.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.libs.CompatLib;
import space.libs.util.cursedmixinextensions.annotations.NewConstructor;
import space.libs.util.cursedmixinextensions.annotations.ShadowSuperConstructor;

@Mixin(GuiYesNo.class)
public class MixinGuiYesNo extends MixinGuiScreen {

    @Shadow
    protected GuiYesNoCallback parentScreen;

    @Shadow
    protected String messageLine1;

    @Shadow
    private String messageLine2;

    @Shadow
    protected String confirmButtonText;

    @Shadow
    protected String cancelButtonText;

    @Shadow
    protected int parentButtonClickedId;

    public GuiScreen field_146355_a;

    @NewConstructor
    public void GuiYesNo(GuiScreen screen, String msg1, String msg2, int buttonId) {
        GuiYesNo(screen, msg1, msg2, I18n.format("gui.yes"), I18n.format("gui.no"), buttonId);
    }

    @NewConstructor
    public void GuiYesNo(GuiScreen screen, String msg1, String msg2, String confirm, String cancel, int buttonId) {
        Object();
        this.field_146355_a = screen;
        try {
            this.parentScreen = (GuiYesNoCallback) screen;
        } catch (ClassCastException e) {
            CompatLib.LOGGER.warn("Legacy Mod Custom GUI " + msg1 + "/" + msg2 + " Doesn't Implement GuiYesNoCallback");
        }
        this.messageLine1 = msg1;
        this.messageLine2 = msg2;
        this.confirmButtonText = confirm;
        this.cancelButtonText = cancel;
        this.parentButtonClickedId = buttonId;
    }

    @ShadowSuperConstructor
    public void Object() {}

    @Inject(method = "actionPerformed", at = @At("HEAD"), cancellable = true)
    protected void actionPerformed(GuiButton button, CallbackInfo ci) {
        if (this.parentScreen == null) {
            this.field_146355_a.confirmClicked(button.id == 0, this.parentButtonClickedId);
            ci.cancel();
        }
    }
}
