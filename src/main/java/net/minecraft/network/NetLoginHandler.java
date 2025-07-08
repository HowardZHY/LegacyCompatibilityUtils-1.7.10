package net.minecraft.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.NetHandler;

public class NetLoginHandler extends NetHandler {

    @Override
    public EntityPlayer getPlayer() {
        return null;
    }
}
