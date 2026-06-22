package space.libs;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.*;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;

import java.util.*;

public class CompatNetworkHandler {

    public static final CompatNetworkHandler INSTANCE = new CompatNetworkHandler();

    public static final List<String> NAMES = new ArrayList<>();

    public static final Map<String, FMLEventChannel> CHANNELS = new HashMap<>();

    public void onInit() {
        NAMES.add(Packet.DEFAULT);
        for (String channelName : NAMES) {
            FMLEventChannel channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(channelName);
            channel.register(this);
            CHANNELS.put(channelName, channel);
        }
    }

    @SubscribeEvent
    public void onPacketFromServer(FMLNetworkEvent.ClientCustomPacketEvent event) {
        processPacket(event);
    }

    @SubscribeEvent
    public void onPacketFromClient(FMLNetworkEvent.ServerCustomPacketEvent event) {
        processPacket(event);
    }

    public static void sendLegacyPacketToPlayer(Packet packet, NetHandlerPlayServer serverHandler) {
        String name = packet.channel();
        FMLEventChannel channel = CHANNELS.get(name);
        if (channel != null) {
            channel.sendTo(packet, serverHandler.playerEntity);
        } else {
            CompatLib.LOGGER.warn("Didn't find channel " + name + "for S2C Legacy Packet " + packet);
        }
    }

    public static void sendLegacyPacketToServer(Packet packet) {
        String name = packet.channel();
        FMLEventChannel channel = CHANNELS.get(name);
        if (channel != null) {
            channel.sendToServer(packet);
        } else {
            CompatLib.LOGGER.warn("Didn't find channel " + name + " for C2S Legacy Packet " + packet);
        }
    }

    public static void addChannel(String channel) {
        if (!NAMES.contains(channel)) {
            NAMES.add(channel);
            CompatLib.LOGGER.info("Registering legacy channel " + channel);
        }
    }

    public static void processPacket(FMLNetworkEvent.CustomPacketEvent<?> event) {
        FMLProxyPacket packet = event.packet;
        if (packet instanceof Packet) {
            Packet legacy = (Packet) packet;
            legacy.func_73279_a((NetHandler) event.handler);
            //TODO read & write PacketData or I/O Stream
        }
    }
}
