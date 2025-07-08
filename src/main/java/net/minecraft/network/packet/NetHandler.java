package net.minecraft.network.packet;

import net.minecraft.entity.player.EntityPlayer;

@SuppressWarnings("unused")
public abstract class NetHandler {

    public void handleVanilla250Packet(Packet250CustomPayload payload) {}

    public abstract EntityPlayer getPlayer();

}
