package net.minecraft.client.multiplayer;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;

public interface INetClientHandler {

    default INetworkManager getNetworkManager() {
        return (INetworkManager) ((NetHandlerPlayClient) this).netManager;
    }

    default void func_74429_a(Packet packet) {
        NetClientHandler.get(this).addToSendQueue(packet);
    }
}
