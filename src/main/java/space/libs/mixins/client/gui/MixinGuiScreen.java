package space.libs.mixins.client.gui;

import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@SuppressWarnings("unused")
@Mixin(GuiScreen.class)
public abstract class MixinGuiScreen {

    @Shadow
    public int width;

    @Shadow
    public int height;

}
