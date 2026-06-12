package space.libs.mixins.forge;

import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.spongepowered.asm.mixin.*;
import space.libs.interfaces.IFMLProxyPacket;

@Mixin(FMLProxyPacket.class)
public abstract class MixinFMLProxyPacket implements IFMLProxyPacket {

    @Shadow
    @Final @Mutable String channel;

    @Shadow
    private @Final @Mutable ByteBuf payload;

    @Override
    public String getChannel() {
        return channel;
    }

    @Override
    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Override
    public void setPayload(byte[] payload) {
        this.payload = Unpooled.wrappedBuffer(payload);
    }
}
