package space.libs.mixins.client.gui;

import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.*;
import space.libs.util.cursedmixinextensions.annotations.NewConstructor;
import space.libs.util.cursedmixinextensions.annotations.ShadowConstructor;

@SuppressWarnings("unused")
@Mixin(GuiConfirmOpenLink.class)
public class MixinGuiConfirmOpenLink {

    @Final
    @Mutable
    @Shadow
    private String openLinkWarning;

    @Final
    @Mutable
    @Shadow
    private String copyLinkButtonText;

    @Final
    @Mutable
    @Shadow
    private String linkText;

    @ShadowConstructor
    public void GuiConfirmOpenLink(GuiYesNoCallback p_i1084_1_, String p_i1084_2_, int p_i1084_3_, boolean p_i1084_4_) {}

    @NewConstructor
    public void GuiConfirmOpenLink(GuiScreen p_i1084_1_, String p_i1084_2_, int p_i1084_3_, boolean p_i1084_4_) {
        this.GuiConfirmOpenLink((GuiYesNoCallback) p_i1084_1_, p_i1084_2_, p_i1084_3_, p_i1084_4_);
    }

}
