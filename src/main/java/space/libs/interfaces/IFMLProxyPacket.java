package space.libs.interfaces;

import net.minecraft.network.Packet;

import java.io.IOException;
import java.util.List;

public interface IFMLProxyPacket {

    List<Packet> toS3FPackets() throws IOException;

}
