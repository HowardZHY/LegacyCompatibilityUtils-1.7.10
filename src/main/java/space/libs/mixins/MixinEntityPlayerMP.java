package space.libs.mixins;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerMP.class)
public class MixinEntityPlayerMP extends MixinEntityPlayer {

    @Shadow
    public NetHandlerPlayServer playerNetServerHandler;

    public int field_71142_cm;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void EntityPlayerMP(MinecraftServer server, WorldServer p_i45285_2_, GameProfile p_i45285_3_, ItemInWorldManager p_i45285_4_, CallbackInfo ci) {
        this.field_71142_cm = server.getConfigurationManager().getViewDistance();
    }

    public void func_71035_c(String msg) {
        this.playerNetServerHandler.sendPacket(new S02PacketChat(new ChatComponentText(msg)));
    }
}
