package net.minecraft.network.packet;

import net.minecraft.network.INetHandler;
import net.minecraft.network.PacketBuffer;
import space.libs.util.MappedName;

/**
 * Old, Dummy Packet Class
 */
public abstract class Packet extends net.minecraft.network.Packet {

    @MappedName("isChunkDataPacket")
    public boolean field_73287_r = false;

    @Override
    public void readPacketData(PacketBuffer data) {}

    @Override
    public void writePacketData(PacketBuffer data) {}

    @Override
    public void processPacket(INetHandler handler) {}

    @Override
    public String toString() {
        return super.toString();
    }
}
