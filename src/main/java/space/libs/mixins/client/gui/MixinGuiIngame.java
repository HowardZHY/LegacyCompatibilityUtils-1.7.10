package space.libs.mixins.client.gui;

import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiNewChat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@SuppressWarnings("unused")
@Mixin(GuiIngame.class)
public abstract class MixinGuiIngame {

    @Shadow
    public abstract GuiNewChat getChatGUI();

    public GuiNewChat func_73827_b() {
        return this.getChatGUI();
    }
}
