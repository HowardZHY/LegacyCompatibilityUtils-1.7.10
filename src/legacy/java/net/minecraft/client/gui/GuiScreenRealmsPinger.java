package net.minecraft.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.mco.McoServer;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.network.*;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.status.INetHandlerStatusClient;
import net.minecraft.network.status.client.C00PacketServerQuery;
import net.minecraft.network.status.client.C01PacketPing;
import net.minecraft.network.status.server.S00PacketServerInfo;
import net.minecraft.network.status.server.S01PacketPong;
import net.minecraft.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SideOnly(Side.CLIENT)
public class GuiScreenRealmsPinger {

    private static final Logger logger = LogManager.getLogger();

    private final List<NetworkManager> field_148509_b = Collections.synchronizedList(new ArrayList<>());

    public void func_148506_a(final McoServer p_148506_1_) throws UnknownHostException {
        if (p_148506_1_.field_148807_g != null) {
            ServerAddress serveraddress = ServerAddress.func_78860_a(p_148506_1_.field_148807_g);
            final NetworkManager networkmanager = NetworkManager.provideLanClient(InetAddress.getByName(serveraddress.getIP()), serveraddress.getPort());
            this.field_148509_b.add(networkmanager);
            networkmanager.setNetHandler(new INetHandlerStatusClient() {

                private boolean field_147399_d = false;

                @Override
                public void handleServerInfo(S00PacketServerInfo p_147397_1_) {
                    ServerStatusResponse serverstatusresponse = p_147397_1_.func_149294_c();
                    if (serverstatusresponse.getPlayerCountData() != null) {
                        p_148506_1_.field_148813_n = EnumChatFormatting.GRAY + "" + serverstatusresponse.getPlayerCountData().getOnlinePlayerCount();
                    }
                    networkmanager.scheduleOutboundPacket(new C01PacketPing(Minecraft.getSystemTime()));
                    this.field_147399_d = true;
                }

                @Override
                public void handlePong(S01PacketPong p_147398_1_) {
                    networkmanager.closeChannel(new ChatComponentText("Finished"));
                }

                @Override
                public void onDisconnect(IChatComponent p_147231_1_) {
                    if (!this.field_147399_d) {
                        GuiScreenRealmsPinger.logger.error("Can't ping " + p_148506_1_.field_148807_g + ": " + p_147231_1_.getUnformattedText());
                    }
                }

                @Override
                public void onConnectionStateTransition(EnumConnectionState p_147232_1_, EnumConnectionState p_147232_2_) {
                    if (p_147232_2_ != EnumConnectionState.STATUS) {
                        throw new UnsupportedOperationException("Unexpected change in protocol to " + p_147232_2_);
                    }
                }

                @Override
                public void onNetworkTick() {}
            });
            try {
                networkmanager.scheduleOutboundPacket(new C00Handshake(4, serveraddress.getIP(), serveraddress.getPort(), EnumConnectionState.STATUS));
                networkmanager.scheduleOutboundPacket(new C00PacketServerQuery());
            } catch (Throwable throwable) {
                logger.error(throwable);
            }
        }
    }

    @SuppressWarnings("unused")
    public void func_148507_b() {
        List<NetworkManager> list = this.field_148509_b;
        synchronized (this.field_148509_b) {
            Iterator<NetworkManager> iterator = this.field_148509_b.iterator();
            while (iterator.hasNext()) {
                NetworkManager networkmanager = iterator.next();
                if (networkmanager.isChannelOpen()) {
                    iterator.remove();
                    networkmanager.closeChannel(new ChatComponentText("Cancelled"));
                }
            }
        }
    }
}
