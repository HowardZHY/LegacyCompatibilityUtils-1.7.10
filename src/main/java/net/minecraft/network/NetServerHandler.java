package net.minecraft.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet250CustomPayload;
import space.libs.fml.network.FMLNetworkHandler;

/**
 * @see net.minecraft.network.NetHandlerPlayServer
 */
public class NetServerHandler extends NetHandler {

    @Override
    public void func_72501_a(Packet250CustomPayload payload) {
        FMLNetworkHandler.handlePacket250Packet(payload, this.getNetworkManager(), this);
    }

    @Override
    public EntityPlayer getPlayer() {
        return get(this).playerEntity;
    }

    public INetworkManager getNetworkManager() {
        return (INetworkManager) get(this).netManager;
    }

    @SuppressWarnings("DataFlowIssue")
    public static NetHandlerPlayServer get(NetServerHandler instance) {
        return ((NetHandlerPlayServer) (Object) instance);
    }
}
