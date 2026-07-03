package space.libs.mixins.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.*;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.libs.CompatNetworkHandler;
import space.libs.util.cursedmixinextensions.annotations.ChangeSuperClass;

@Mixin(NetHandlerPlayServer.class)
@ChangeSuperClass(NetServerHandler.class)
public abstract class MixinNetHandlerPlayServer implements INetServerHandler {

    public INetworkManager field_72575_b;

    @Shadow
    public void sendPacket(Packet packetIn) {}

    @Override
    public INetworkManager getNetworkManager() {
        return this.field_72575_b;
    }

    @Override
    public void func_72567_b(net.minecraft.network.packet.Packet packet) {
        this.sendPacket(packet);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    public void init(MinecraftServer server, NetworkManager manager, EntityPlayerMP player, CallbackInfo ci) {
        this.field_72575_b = (INetworkManager) manager;
    }

    @Inject(method = "sendPacket", at = @At("HEAD"), cancellable = true)
    public void sendPacket(Packet packetIn, CallbackInfo ci) {
        if (packetIn instanceof net.minecraft.network.packet.Packet) {
            CompatNetworkHandler.sendLegacyPacketToPlayer(
                (net.minecraft.network.packet.Packet) packetIn,
                (NetHandlerPlayServer) (Object) this
            );
            ci.cancel();
        }
    }
}
