package net.minecraft.network.packet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.INetHandler;
import net.minecraft.util.IChatComponent;

@SuppressWarnings("unused")
public abstract class NetHandler implements INetHandler {

    public boolean func_72469_b() {
        return false;
    }

    public boolean func_142032_c() {
        return false;
    }

    public void func_72455_a(Packet1Login packet1Login) {}

    public void func_72501_a(Packet250CustomPayload payload) {}

    public void handleVanilla250Packet(Packet250CustomPayload payload) {
        // NO-OP
    }

    public abstract EntityPlayer getPlayer();

    public void onDisconnect(IChatComponent reason) {}

    public void onConnectionStateTransition(EnumConnectionState oldState, EnumConnectionState newState) {}

    public void onNetworkTick() {}

}
