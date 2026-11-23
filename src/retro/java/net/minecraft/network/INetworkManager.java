package net.minecraft.network;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.packet.NetHandler;
import space.libs.util.MappedName;

import java.net.SocketAddress;

public interface INetworkManager {

    @MappedName("setNetHandler")
    default void func_74425_a(NetHandler handler) {}

    /**
     * Adds the packet to the correct send queue (chunk data packets go to a separate queue).
     */
    @MappedName("addToSendQueue")
    default void func_74429_a(Packet packet) {
        INetHandler handler = this.getNetHandler();
        if (handler instanceof NetHandlerPlayServer) {
            NetHandlerPlayServer serverHandler = (NetHandlerPlayServer) handler;
            serverHandler.sendPacket(packet);
        } else if (handler instanceof NetHandlerPlayClient) {
            NetHandlerPlayClient clientHandler = (NetHandlerPlayClient) handler;
            clientHandler.addToSendQueue(packet);
        }
    }

    @MappedName("wakeThreads")
    default void func_74427_a() {}

    @MappedName("processReadPackets")
    default void func_74428_b() {}

    @MappedName("getSocketAddress")
    SocketAddress func_74430_c();

    @MappedName("serverShutdown")
    default void func_74423_d() {}

    @MappedName("packetSize")
    default int func_74426_e() {
        return 0;
    }

    @MappedName("networkShutdown")
    default void func_74424_a(String s, Object... args) {}

    @MappedName("closeConnections")
    default void func_74431_f() {}

    INetHandler getNetHandler();

}
