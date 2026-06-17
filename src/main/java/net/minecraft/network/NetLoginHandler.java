package net.minecraft.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.*;
import space.libs.fml.network.FMLNetworkHandler;

public class NetLoginHandler extends NetHandler {

    @Override
    public void func_72455_a(Packet1Login packet1Login) {
        FMLNetworkHandler.handleLoginPacketOnServer(this, packet1Login);
    }

    @Override
    public EntityPlayer getPlayer() {
        return null;
    }
}
