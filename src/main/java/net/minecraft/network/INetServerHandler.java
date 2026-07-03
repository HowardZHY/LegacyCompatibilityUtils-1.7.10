package net.minecraft.network;

public interface INetServerHandler {

    default INetworkManager getNetworkManager() {
        return (INetworkManager) ((NetHandlerPlayServer) this).netManager;
    }

    default void func_72567_b(net.minecraft.network.packet.Packet packet) {
        NetServerHandler.get(this).sendPacket(packet);
    }
}
