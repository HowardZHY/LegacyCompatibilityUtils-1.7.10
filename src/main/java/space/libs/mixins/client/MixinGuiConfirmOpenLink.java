package space.libs.mixins.client;

import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNoCallback;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.util.cursedmixinextensions.annotations.NewConstructor;
import space.libs.util.cursedmixinextensions.annotations.ShadowConstructor;

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
    private String field_146361_t;

    @ShadowConstructor
    public void GuiConfirmOpenLink(GuiYesNoCallback p_i1084_1_, String p_i1084_2_, int p_i1084_3_, boolean p_i1084_4_) {}

    @NewConstructor
    public void GuiConfirmOpenLink(GuiScreen p_i1084_1_, String p_i1084_2_, int p_i1084_3_, boolean p_i1084_4_) {
        this.GuiConfirmOpenLink((GuiYesNoCallback) p_i1084_1_, p_i1084_2_, p_i1084_3_, p_i1084_4_);
    }

}
