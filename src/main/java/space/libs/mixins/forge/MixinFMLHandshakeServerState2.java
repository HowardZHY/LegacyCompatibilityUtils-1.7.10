package space.libs.mixins.forge;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.network.handshake.FMLHandshakeMessage;
import cpw.mods.fml.common.network.handshake.NetworkDispatcher;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import io.netty.channel.ChannelHandlerContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import space.libs.interfaces.INetworkDispatcher;

import java.lang.reflect.Field;

@Mixin(targets = "cpw.mods.fml.common.network.handshake.FMLHandshakeServerState$2", remap = false)
public class MixinFMLHandshakeServerState2 {

    private static Field ERROR;

    private static Field WAITINGCACK;

    private static void getField() {
        try {
            Class<?> c = Class.forName("cpw.mods.fml.common.network.handshake.FMLHandshakeServerState");
            Field f = c.getField("ERROR");
            Field f1 = c.getField("WAITINGCACK");
            f.setAccessible(true);
            f1.setAccessible(true);
            ERROR = f;
            WAITINGCACK = f1;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("all")
    @Inject(method = "accept", at = @At("HEAD"), cancellable = true)
    private void accept(ChannelHandlerContext ctx, FMLHandshakeMessage msg, CallbackInfoReturnable cir) {
        getField();
        if (msg instanceof FMLHandshakeMessage.ClientHello) {
            FMLLog.info("Client protocol version %x", ((FMLHandshakeMessage.ClientHello)msg).protocolVersion());
            cir.setReturnValue(this);
        } else {
            FMLHandshakeMessage.ModList client = (FMLHandshakeMessage.ModList) msg;
            NetworkDispatcher dispatcher = ctx.channel().attr(NetworkDispatcher.FML_DISPATCHER).get();
            INetworkDispatcher accessor = (INetworkDispatcher) dispatcher;
            accessor.setModList(client.modList());
            FMLLog.info("Client attempting to join with %d mods : %s", client.modListSize(), client.modListAsString());
            String result = FMLNetworkHandler.checkModList(client, Side.CLIENT);
            try {
                if (result != null) {
                    dispatcher.rejectHandshake(result);
                    cir.setReturnValue(ERROR.get(null));
                }
                ctx.writeAndFlush(new FMLHandshakeMessage.ModList(Loader.instance().getActiveModList()));
                cir.setReturnValue(WAITINGCACK.get(null));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
