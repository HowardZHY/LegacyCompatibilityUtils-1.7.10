package space.libs.mixins.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ChatComponentText;
import org.spongepowered.asm.mixin.Mixin;
import space.libs.mixins.MixinEntityPlayer;

@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP extends MixinEntityPlayer {

    @Override
    public void func_71035_c(String msg) {
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(msg));
    }

}
