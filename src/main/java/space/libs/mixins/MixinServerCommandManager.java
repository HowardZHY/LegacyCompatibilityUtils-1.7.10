package space.libs.mixins;

import com.google.common.collect.Lists;
import net.minecraft.command.IAdminCommandLegacy;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import org.spongepowered.asm.mixin.Mixin;
import space.libs.util.MappedName;

import java.util.*;

@SuppressWarnings("all")
@Mixin(ServerCommandManager.class)
public class MixinServerCommandManager implements IAdminCommandLegacy {

    @MappedName("notifyAdmins")
    @Override
    public void func_71563_a(ICommandSender sender, int i, String msgFormat, Object ... msgParams) {
        boolean flag = true;
        if (sender instanceof TileEntityCommandBlock && !MinecraftServer.getServer().worldServers[0].getGameRules().getGameRuleBooleanValue("commandBlockOutput")) {
            flag = false;
        }
        ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("chat.type.admin", sender.getCommandSenderName(), new ChatComponentTranslation(msgFormat, msgParams));
        chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.GRAY);
        chatComponentTranslation.getChatStyle().setItalic(Boolean.TRUE);
        if (flag) {
            ArrayList<EntityPlayerMP> plist = Lists.newArrayList(MinecraftServer.getServer().getConfigurationManager().playerEntityList);
            for (EntityPlayerMP player : plist) {
                if (player != sender && MinecraftServer.getServer().getConfigurationManager().canSendCommands(player.getGameProfile())) {
                    player.addChatMessage(chatComponentTranslation);
                }
            }
        }
        if (sender != MinecraftServer.getServer()) {
            MinecraftServer.getServer().addChatMessage(chatComponentTranslation);
        }
        if ((i & 1) != 1) {
            sender.addChatMessage(new ChatComponentTranslation(msgFormat, msgParams));
        }
    }

}
