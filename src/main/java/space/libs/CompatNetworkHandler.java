package space.libs;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.*;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.*;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.packet.*;
import space.libs.interfaces.IFMLProxyPacket;

import java.io.*;
import java.util.*;

public class CompatNetworkHandler {

    public static final CompatNetworkHandler INSTANCE = new CompatNetworkHandler();

    public static final List<String> NAMES = new ArrayList<>();

    public static final Map<String, FMLEventChannel> CHANNELS = new HashMap<>();

    public void onInit() {
        NAMES.add(Packet.DEFAULT);
        NAMES.add("REGISTER");
        NAMES.add("UNREGISTER");
        for (String channelName : NAMES) {
            CompatLib.LOGGER.info("Registering legacy channel " + channelName);
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
        validate(packet);
        String name = packet.channel();
        FMLEventChannel channel = CHANNELS.get(name);
        if (channel != null) {
            packet.setTarget(Side.SERVER);
            channel.sendTo(packet, serverHandler.playerEntity);
        } else {
            CompatLib.LOGGER.warn("Didn't find channel " + name + " for S2C Legacy Packet " + packet);
        }
    }

    public static void sendLegacyPacketToServer(Packet packet) {
        validate(packet);
        String name = packet.channel();
        FMLEventChannel channel = CHANNELS.get(name);
        if (channel != null) {
            packet.setTarget(Side.CLIENT);
            channel.sendToServer(packet);
        } else {
            CompatLib.LOGGER.warn("Didn't find channel " + name + " for C2S Legacy Packet " + packet);
        }
    }

    public static void addChannel(String channel) {
        if (!NAMES.contains(channel)) {
            NAMES.add(channel);
        }
    }

    public static void processPacket(FMLNetworkEvent.CustomPacketEvent<?> event) {
        FMLProxyPacket packet = event.packet;
        String channel = packet.channel();
        //CompatLib.LOGGER.info(event + " " + packet + " " + channel);
        if (!NAMES.contains(channel)) {
            return;
        }
        ByteBuf buf = packet.payload();
        int len = buf.readableBytes();
        byte[] data = new byte[len];
        buf.getBytes(buf.readerIndex(), data, 0, len);
        Packet legacy = new Packet250CustomPayload(channel, data);
        legacy.setTarget(packet.getTarget());
        if (legacy.raw) {
            readPacketData(legacy); //TODO - for other packets
        }
        legacy.func_73279_a((NetHandler) event.handler);
    }

    public static void readPacketData(Packet packet) {
        try {
            ByteBuf payload = packet.payload();
            DataInput in = new DataInputStream(new ByteBufInputStream(payload, payload.readableBytes()));
            packet.func_73267_a(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writePacketData(Packet packet) {
        try {
            ByteBuf payload = Unpooled.buffer();
            DataOutput out = new DataOutputStream(new ByteBufOutputStream(payload));
            packet.func_73273_a(out);
            ((IFMLProxyPacket) packet).setPayload(payload);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void validate(Packet packet) {
        IFMLProxyPacket accessor = (IFMLProxyPacket) packet;
        if (packet instanceof Packet250CustomPayload) {
            Packet250CustomPayload payload = (Packet250CustomPayload) packet;
            byte[] data = payload.field_73629_c;
            if (data == null) {
                data = new byte[0];
            }
            payload.field_73628_b = data.length;
            accessor.setChannel(payload.field_73630_a);
            accessor.setPayloadBytes(data);
            return;
        }
        if (packet.raw) {
            writePacketData(packet);
        }
    }
}
