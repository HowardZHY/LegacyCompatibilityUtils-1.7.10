package net.minecraft.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.NetHandler;

public class NetServerHandler extends NetHandler {

    @Override
    public EntityPlayer getPlayer() {
        return null; //NetHandlerPlayServer.playerEntity
    }
}
