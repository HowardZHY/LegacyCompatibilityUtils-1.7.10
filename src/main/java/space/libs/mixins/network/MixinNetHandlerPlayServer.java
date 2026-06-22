package space.libs.mixins.network;

import net.minecraft.network.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.libs.CompatNetworkHandler;
import space.libs.util.cursedmixinextensions.annotations.ChangeSuperClass;

@Mixin(NetHandlerPlayServer.class)
@ChangeSuperClass(NetServerHandler.class)
public abstract class MixinNetHandlerPlayServer {

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
