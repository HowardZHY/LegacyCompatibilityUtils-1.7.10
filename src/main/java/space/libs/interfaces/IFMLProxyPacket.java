package space.libs.interfaces;

@SuppressWarnings("unused")
public interface IFMLProxyPacket {

    String getChannel();

    void setChannel(String channel);

    void setPayload(byte[] payload);

}
