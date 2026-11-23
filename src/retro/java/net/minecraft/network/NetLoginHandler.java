package net.minecraft.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.NetHandlerLoginServer;
import space.libs.fml.network.FMLNetworkHandler;
import space.libs.util.MappedName;

import javax.crypto.SecretKey;

/**
 * @see net.minecraft.server.network.NetHandlerLoginServer
 */
public class NetLoginHandler extends NetHandler {

    public volatile boolean field_72544_i;

    @Override
    public void func_72455_a(Packet1Login packet1Login) {
        FMLNetworkHandler.handleLoginPacketOnServer(this, packet1Login);
    }

    @Override
    public void func_72501_a(Packet250CustomPayload payload) {
        FMLNetworkHandler.handlePacket250Packet(payload, this.getNetworkManager(), this);
    }

    @MappedName("initializePlayerConnection")
    public void func_72529_d() {
        FMLNetworkHandler.onConnectionReceivedFromClient(this, func_72530_b(this), this.getNetworkManager().func_74430_c(), func_72533_d(this));
    }

    @MappedName("isServerHandler")
    public boolean func_72489_a() {
        return true;
    }

    @Override
    public boolean func_142032_c() {
        return !get(this).networkManager.channel().isOpen();
    }

    @MappedName("getServerId")
    public static String func_72526_a(NetLoginHandler handler) {
        return get(handler).serverId;
    }

    @MappedName("getLoginMinecraftServer")
    public static MinecraftServer func_72530_b(NetLoginHandler handler) {
        return get(handler).server;
    }

    @MappedName("getSharedKey")
    public static SecretKey func_72525_c(NetLoginHandler handler) {
        return get(handler).secretKey;
    }

    @MappedName("getClientUsername")
    public static String func_72533_d(NetLoginHandler handler) {
        return get(handler).loginGameProfile.getName();
    }

    public static boolean func_72531_a(NetLoginHandler handler, boolean par1) {
        return handler.field_72544_i = par1;
    }

    @Override
    public EntityPlayer getPlayer() {
        return null;
    }

    public INetworkManager getNetworkManager() {
        return (INetworkManager) get(this).networkManager;
    }

    @SuppressWarnings("DataFlowIssue")
    public static NetHandlerLoginServer get(NetLoginHandler instance) {
        return ((NetHandlerLoginServer) (Object) instance);
    }
}
