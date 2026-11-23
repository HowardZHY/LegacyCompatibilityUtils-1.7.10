package net.minecraft.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.*;
import space.libs.fml.network.FMLNetworkHandler;
import space.libs.util.MappedName;

/**
 * @see net.minecraft.network.NetHandlerPlayServer
 */
public class NetServerHandler extends NetHandler implements INetServerHandler {

    @Override
    public void func_72494_a(Packet131MapData par1Packet131MapData) {
        FMLNetworkHandler.handlePacket131Packet(this, par1Packet131MapData);
    }

    @Override
    public void func_72501_a(Packet250CustomPayload payload) {
        FMLNetworkHandler.handlePacket250Packet(payload, this.getNetworkManager(), this);
    }

    @Override
    @MappedName("sendPacketToPlayer")
    public void func_72567_b(net.minecraft.network.packet.Packet packet) {
        this.sendPacketToPlayer(packet);
    }

    public void sendPacketToPlayer(net.minecraft.network.Packet packet) {
        get(this).sendPacket(packet);
    }

    @Override
    public EntityPlayer getPlayer() {
        return get(this).playerEntity;
    }

    public static NetHandlerPlayServer get(INetServerHandler instance) {
        return ((NetHandlerPlayServer) instance);
    }
}
