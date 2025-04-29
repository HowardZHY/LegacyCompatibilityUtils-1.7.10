package space.libs.mixins.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.util.cursedmixinextensions.annotations.Public;

@SuppressWarnings("unused")
@Mixin(GuiSelectWorld.class)
public class MixinGuiSelectWorld {

    @Shadow
    public static GuiYesNo func_152129_a(GuiYesNoCallback p_152129_0_, String p_152129_1_, int p_152129_2_) {
        throw new AbstractMethodError();
    }

    @Public
    private static GuiYesNo func_146623_a(GuiScreen p_146623_0_, String p_146623_1_, int p_146623_2_) {
        return func_152129_a((GuiYesNoCallback) p_146623_0_, p_146623_1_, p_146623_2_);
    }

}
