package space.libs.mixins.client;

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
import space.libs.util.cursedmixinextensions.annotations.ShadowConstructor;
import space.libs.util.cursedmixinextensions.annotations.ShadowSuperConstructor;

@Mixin(GuiYesNo.class)
public class MixinGuiYesNo {

    @Shadow
    protected GuiYesNoCallback parentScreen;

    @Shadow
    protected String field_146351_f;

    @Shadow
    private String field_146354_r;

    @Shadow
    protected String confirmButtonText;

    @Shadow
    protected String cancelButtonText;

    @Shadow
    protected int field_146357_i;

    public GuiScreen field_146355_a;

    @NewConstructor
    public void GuiYesNo(GuiScreen screen, String p_i1082_2_, String p_i1082_3_, int p_i1082_4_) {
        Object();
        this.field_146355_a = screen;
        try {
            this.parentScreen = (GuiYesNoCallback) screen;
        } catch (ClassCastException e) {
            CompatLib.LOGGER.warn("Legacy Mod Custom GUI " + p_i1082_2_ + "/" + p_i1082_3_ + " Doesn't Implement GuiYesNoCallback");
        }
        this.field_146351_f = p_i1082_2_;
        this.field_146354_r = p_i1082_3_;
        this.field_146357_i = p_i1082_4_;
        this.confirmButtonText = I18n.format("gui.yes");
        this.cancelButtonText = I18n.format("gui.no");
    }

    @NewConstructor
    public void GuiYesNo(GuiScreen screen, String p_i1083_2_, String p_i1083_3_, String p_i1083_4_, String p_i1083_5_, int p_i1083_6_) {
        Object();
        this.field_146355_a = screen;
        try {
            this.parentScreen = (GuiYesNoCallback) screen;
        } catch (ClassCastException e) {
            CompatLib.LOGGER.warn("Legacy Mod Custom GUI " + p_i1083_2_ + "/" + p_i1083_3_ + " Doesn't Implement GuiYesNoCallback");
        }
        this.field_146351_f = p_i1083_2_;
        this.field_146354_r = p_i1083_3_;
        this.confirmButtonText = p_i1083_4_;
        this.cancelButtonText = p_i1083_5_;
        this.field_146357_i = p_i1083_6_;
    }

    @ShadowSuperConstructor
    public void Object() {}

    @Inject(method = "actionPerformed", at = @At("HEAD"), cancellable = true)
    protected void actionPerformed(GuiButton button, CallbackInfo ci) {
        if (this.parentScreen == null) {
            this.field_146355_a.confirmClicked(button.id == 0, this.field_146357_i);
            ci.cancel();
        }
    }
}
