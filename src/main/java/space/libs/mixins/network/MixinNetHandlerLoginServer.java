package space.libs.mixins.network;

import net.minecraft.network.NetLoginHandler;
import net.minecraft.server.network.NetHandlerLoginServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.libs.util.cursedmixinextensions.annotations.ChangeSuperClass;

@Mixin(NetHandlerLoginServer.class)
@ChangeSuperClass(NetLoginHandler.class)
public abstract class MixinNetHandlerLoginServer {

    @SuppressWarnings("DataFlowIssue")
    @Inject(
        method = "func_147326_c",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/network/NetworkManager;scheduleOutboundPacket(Lnet/minecraft/network/Packet;[Lio/netty/util/concurrent/GenericFutureListener;)V",
            shift = At.Shift.AFTER
        )
    )
    public void tryAcceptPlayer(CallbackInfo ci) {
        ((NetLoginHandler) (Object) this).func_72529_d();
    }
}
