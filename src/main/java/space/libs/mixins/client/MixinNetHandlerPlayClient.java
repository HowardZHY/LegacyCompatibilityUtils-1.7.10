package space.libs.mixins.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.INetClientHandler;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.*;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.server.integrated.IntegratedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.libs.CompatNetworkHandler;
import space.libs.fml.network.FMLNetworkHandler;
import space.libs.util.cursedmixinextensions.annotations.ChangeSuperClass;

import java.net.InetSocketAddress;

@Mixin(NetHandlerPlayClient.class)
@ChangeSuperClass(NetClientHandler.class)
public abstract class MixinNetHandlerPlayClient implements INetClientHandler {

    public INetworkManager field_72555_g;

    @Shadow
    public void addToSendQueue(Packet packet) {}

    @Override
    public INetworkManager getNetworkManager() {
        return field_72555_g;
    }

    @Override
    public void func_74429_a(net.minecraft.network.packet.Packet packet) {
        this.addToSendQueue(packet);
    }

    @SuppressWarnings("DataFlowIssue")
    @Inject(method = "<init>", at = @At("RETURN"))
    public void init(Minecraft mc, GuiScreen screen, NetworkManager manager, CallbackInfo ci) {
        NetClientHandler This = (NetClientHandler) (Object) this;
        this.field_72555_g = (INetworkManager) manager;
        IntegratedServer server = mc.getIntegratedServer();
        if (server != null) {
            FMLNetworkHandler.onClientConnectionToIntegratedServer(This, server, this.field_72555_g);
        } else {
            InetSocketAddress address = (InetSocketAddress) manager.channel().remoteAddress();
            String ip = address.getHostString();
            int port = address.getPort();
            FMLNetworkHandler.onClientConnectionToRemoteServer(This, ip, port, this.field_72555_g);
        }
    }

    @Inject(method = "handleJoinGame", at = @At("HEAD"))
    public void handleJoinGame(S01PacketJoinGame packet, CallbackInfo ci) {
        NetClientHandler.getLegacy(this).func_72455_a(
            new Packet1Login(
                packet.func_149197_c(),
                packet.func_149196_i(),
                packet.func_149198_e(),
                packet.func_149195_d(),
                packet.func_149194_f(),
                packet.func_149192_g().getDifficultyId(),
                packet.func_149193_h(),
                16
            )
        );
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
