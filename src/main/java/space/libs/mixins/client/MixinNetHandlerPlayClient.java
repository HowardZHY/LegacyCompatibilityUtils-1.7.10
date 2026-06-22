package space.libs.mixins.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.*;
import net.minecraft.server.integrated.IntegratedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.libs.CompatNetworkHandler;
import space.libs.fml.network.FMLNetworkHandler;
import space.libs.util.cursedmixinextensions.annotations.ChangeSuperClass;

import java.net.InetSocketAddress;

@Mixin(NetHandlerPlayClient.class)
@ChangeSuperClass(NetClientHandler.class)
public abstract class MixinNetHandlerPlayClient {

    @SuppressWarnings("DataFlowIssue")
    @Inject(method = "<init>", at = @At("RETURN"))
    public void init(Minecraft mc, GuiScreen screen, NetworkManager manager, CallbackInfo ci) {
        NetClientHandler This = (NetClientHandler) (Object) this;
        INetworkManager iManager = (INetworkManager) manager;
        IntegratedServer server = mc.getIntegratedServer();
        if (server != null) {
            FMLNetworkHandler.onClientConnectionToIntegratedServer(This, server,  iManager);
        } else {
            InetSocketAddress address = (InetSocketAddress) manager.channel().remoteAddress();
            String ip = address.getHostString();
            int port = address.getPort();
            FMLNetworkHandler.onClientConnectionToRemoteServer(This, ip, port, iManager);
        }
    }

    @Inject(method = "addToSendQueue", at = @At("HEAD"), cancellable = true)
    public void addToSendQueue(Packet packetIn, CallbackInfo ci) {
        if (packetIn instanceof net.minecraft.network.packet.Packet) {
            CompatNetworkHandler.sendLegacyPacketToServer(
                (net.minecraft.network.packet.Packet) packetIn
            );
            ci.cancel();
        }
    }
}
