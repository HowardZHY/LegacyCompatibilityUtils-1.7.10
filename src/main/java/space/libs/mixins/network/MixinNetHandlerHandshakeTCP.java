package space.libs.mixins.network;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.server.network.NetHandlerHandshakeTCP;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.libs.interfaces.IFMLCommonHandler;

@Mixin(value = NetHandlerHandshakeTCP.class, priority = 1)
public class MixinNetHandlerHandshakeTCP {

    @Shadow
    private @Final NetworkManager field_147386_b;

    @Dynamic
    @Inject(method = "processHandshake", at = @At(value = "HEAD"), cancellable = true)
    private void processHandshake(C00Handshake packetIn, CallbackInfo ci) {
        IFMLCommonHandler accessor = (IFMLCommonHandler) FMLCommonHandler.instance();
        if (!accessor.handleServerHandshake(packetIn, this.field_147386_b)) {
            ci.cancel();
        }
    }
}
