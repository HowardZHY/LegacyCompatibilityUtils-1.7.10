package space.libs.interfaces;

import io.netty.buffer.ByteBuf;

@SuppressWarnings("unused")
public interface IFMLProxyPacket {

    String getChannel();

    void setChannel(String channel);

    void setPayload(ByteBuf payload);

    void setPayloadBytes(byte[] payload);

}
