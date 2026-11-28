package space.libs.mixins.forge;

import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.simpleimpl.*;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.network.INetHandler;
import org.spongepowered.asm.mixin.Mixin;

@SuppressWarnings("unused")
@Mixin(value = SimpleNetworkWrapper.class, remap = false)
public abstract class MixinSimpleNetworkWrapper {

    public <REQ extends IMessage, REPLY extends IMessage, NH extends INetHandler> void addServerHandlerAfter(FMLEmbeddedChannel channel, String type, Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestType) {
        SimpleChannelHandlerWrapper<REQ, REPLY> handler = getHandlerWrapper(messageHandler, Side.SERVER, requestType);
        channel.pipeline().addAfter(type, messageHandler.getName(), handler);
    }

    public <REQ extends IMessage, REPLY extends IMessage, NH extends INetHandler> void addClientHandlerAfter(FMLEmbeddedChannel channel, String type, Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestType) {
        SimpleChannelHandlerWrapper<REQ, REPLY> handler = getHandlerWrapper(messageHandler, Side.CLIENT, requestType);
        channel.pipeline().addAfter(type, messageHandler.getName(), handler);
    }

    public <REPLY extends IMessage, REQ extends IMessage> SimpleChannelHandlerWrapper<REQ, REPLY> getHandlerWrapper(Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Side side, Class<REQ> requestType) {
        return new SimpleChannelHandlerWrapper<>(messageHandler, side, requestType);
    }
}
