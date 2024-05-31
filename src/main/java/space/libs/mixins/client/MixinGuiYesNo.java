package space.libs.mixins.client;

import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNoCallback;
import org.spongepowered.asm.mixin.Mixin;
import space.libs.util.cursedmixinextensions.annotations.NewConstructor;
import space.libs.util.cursedmixinextensions.annotations.ShadowConstructor;

@Mixin(GuiYesNo.class)
public class MixinGuiYesNo {

    public GuiScreen field_146355_a;

    @NewConstructor
    public void GuiYesNo(GuiScreen p_i1082_1_, String p_i1082_2_, String p_i1082_3_, int p_i1082_4_) {
        this.field_146355_a = p_i1082_1_;
        this.GuiYesNo((GuiYesNoCallback) p_i1082_1_, p_i1082_2_, p_i1082_3_, p_i1082_4_);
    }

    @NewConstructor
    public void GuiYesNo(GuiScreen p_i1083_1_, String p_i1083_2_, String p_i1083_3_, String p_i1083_4_, String p_i1083_5_, int p_i1083_6_) {
        this.field_146355_a = p_i1083_1_;
        this.GuiYesNo((GuiYesNoCallback) p_i1083_1_, p_i1083_2_, p_i1083_3_, p_i1083_4_, p_i1083_5_, p_i1083_6_);
    }

    @ShadowConstructor
    public void GuiYesNo(GuiYesNoCallback p_i1082_1_, String p_i1082_2_, String p_i1082_3_, int p_i1082_4_) {}

    @ShadowConstructor
    public void GuiYesNo(GuiYesNoCallback p_i1083_1_, String p_i1083_2_, String p_i1083_3_, String p_i1083_4_, String p_i1083_5_, int p_i1083_6_) {}

}
