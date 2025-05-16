package space.libs.mixins.client.gui;

import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@SuppressWarnings("unused")
@Mixin(GuiNewChat.class)
public class MixinGuiNewChat {

    @Shadow
    public void printChatMessage(IChatComponent p_146227_1_) {}

    public void func_73765_a(String msg) {
        this.printChatMessage(new ChatComponentText(msg));
    }
}
