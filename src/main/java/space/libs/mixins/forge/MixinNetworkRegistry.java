/*
 * Forge Mod Loader
 * Copyright (c) 2012-2013 cpw.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * https://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 *
 * Contributors:
 *     cpw - implementation
 */
package space.libs.mixins.forge;

import com.google.common.base.*;
import com.google.common.collect.*;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.discovery.ASMDataTable;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.libs.CompatNetworkHandler;
import space.libs.fml.network.*;
import space.libs.interfaces.INetworkRegistry;
import space.libs.util.cursedmixinextensions.annotations.Public;

import java.util.*;

@SuppressWarnings("unused")
@Mixin(value = NetworkRegistry.class, remap = false)
public class MixinNetworkRegistry implements INetworkRegistry {

    @Shadow
    public static @Final NetworkRegistry INSTANCE;

    @Public
    private static NetworkRegistry instance() {
        return INSTANCE;
    }

    public Multimap<Player, String> activeChannels = ArrayListMultimap.create();

    public Multimap<String, IPacketHandler> universalPacketHandlers = ArrayListMultimap.create();

    public Multimap<String, IPacketHandler> clientPacketHandlers = ArrayListMultimap.create();

    public Multimap<String, IPacketHandler> serverPacketHandlers = ArrayListMultimap.create();

    public Set<IConnectionHandler> connectionHandlers = Sets.newLinkedHashSet();

    public List<IChatListener> chatListeners = Lists.newArrayList();

    public byte[] getPacketRegistry(Side side) {
        return Joiner.on('\0').join(
            Iterables.concat(
                Collections.singletonList("FML"),
                universalPacketHandlers.keySet(),
                side.isClient() ? clientPacketHandlers.keySet() : serverPacketHandlers.keySet()
            )
        ).getBytes(Charsets.UTF_8);
    }

    @Override
    public void registerChannel(IPacketHandler handler, String channelName) {
        if (Strings.isNullOrEmpty(channelName) || channelName.length() > 16) {
            FMLLog.severe("Invalid channel name '%s' : %s", channelName, Strings.isNullOrEmpty(channelName) ? "Channel name is empty" : "Channel name is too long (16 chars is maximum)");
            throw new RuntimeException("Channel name is invalid");
        }
        universalPacketHandlers.put(channelName, handler);
        CompatNetworkHandler.addChannel(channelName);
    }

    @Override
    public void registerChannel(IPacketHandler handler, String channelName, Side side) {
        if (side == null) {
            registerChannel(handler, channelName);
            return;
        }
        if (Strings.isNullOrEmpty(channelName) || channelName.length() > 16) {
            FMLLog.severe("Invalid channel name '%s' : %s", channelName, Strings.isNullOrEmpty(channelName) ? "Channel name is empty" : "Channel name is too long (16 chars is maximum)");
            throw new RuntimeException("Channel name is invalid");
        }
        if (side.isClient()) {
            clientPacketHandlers.put(channelName, handler);
        } else {
            serverPacketHandlers.put(channelName, handler);
        }
        CompatNetworkHandler.addChannel(channelName);
    }

    public void activateChannel(Player player, String channel) {
        activeChannels.put(player, channel);
    }

    public void deactivateChannel(Player player, String channel) {
        activeChannels.remove(player, channel);
    }

    @Override
    public void registerConnectionHandler(IConnectionHandler handler) {
        connectionHandlers.add(handler);
    }

    @Override
    public void registerChatListener(IChatListener listener) {
        chatListeners.add(listener);
    }

    @Override
    public void playerLoggedIn(EntityPlayerMP player, NetServerHandler netHandler, INetworkManager manager) {
        generateChannelRegistration(player, netHandler, manager);
        for (IConnectionHandler handler : connectionHandlers) {
            handler.playerLoggedIn((Player)player, netHandler, manager);
        }
    }

    @Override
    public String connectionReceived(NetLoginHandler netHandler, INetworkManager manager) {
        for (IConnectionHandler handler : connectionHandlers) {
            String kick = handler.connectionReceived(netHandler, manager);
            if (!Strings.isNullOrEmpty(kick)) {
                return kick;
            }
        }
        return null;
    }

    @Override
    public void connectionOpened(NetHandler netClientHandler, String server, int port, INetworkManager networkManager) {
        for (IConnectionHandler handler : connectionHandlers) {
            handler.connectionOpened(netClientHandler, server, port, networkManager);
        }
    }

    @Override
    public void connectionOpened(NetHandler netHandler, MinecraftServer server, INetworkManager networkManager) {
        for (IConnectionHandler handler : connectionHandlers) {
            handler.connectionOpened(netHandler, server, networkManager);
        }
    }

    @Override
    public void clientLoggedIn(NetHandler clientHandler, INetworkManager manager, Packet1Login login) {
        generateChannelRegistration(clientHandler.getPlayer(), clientHandler, manager);
        for (IConnectionHandler handler : connectionHandlers) {
            handler.clientLoggedIn(clientHandler, manager, login);
        }
    }

    public void generateChannelRegistration(EntityPlayer player, NetHandler netHandler, INetworkManager manager) {
        String channel = "REGISTER";
        byte[] data = getPacketRegistry(player instanceof EntityPlayerMP ? Side.SERVER : Side.CLIENT);
        Packet250CustomPayload pkt = new Packet250CustomPayload(channel, data);
        pkt.field_73628_b = pkt.field_73629_c.length;
        if (netHandler instanceof NetServerHandler) {
            NetHandlerPlayServer handlerPlayServer = NetServerHandler.get((NetServerHandler) netHandler);
            handlerPlayServer.sendPacket(pkt.toS3FPacket());
        } else if (netHandler instanceof NetClientHandler) {
            NetHandlerPlayClient handlerPlayClient = NetClientHandler.get((NetClientHandler) netHandler);
            handlerPlayClient.addToSendQueue(pkt.toC17Packet());
        }
        manager.func_74429_a(pkt);
    }

    @Override
    public void connectionClosed(INetworkManager manager, EntityPlayer player) {
        for (IConnectionHandler handler : connectionHandlers) {
            handler.connectionClosed(manager);
        }
        activeChannels.removeAll((Player) player);
    }

    @Override
    public void handleCustomPacket(Packet250CustomPayload packet, INetworkManager network, NetHandler handler) {
        if ("REGISTER".equals(packet.field_73630_a)) {
            this.handleRegistrationPacket(packet, (Player)handler.getPlayer());
        } else if ("UNREGISTER".equals(packet.field_73630_a)) {
            this.handleUnregistrationPacket(packet, (Player)handler.getPlayer());
        } else {
            this.handlePacket(packet, network, (Player)handler.getPlayer());
        }
    }

    @Override
    public void handlePacket(Packet250CustomPayload packet, INetworkManager network, Player player) {
        String channel = packet.field_73630_a;
        for (
            IPacketHandler handler :
            Iterables.concat(
                universalPacketHandlers.get(channel),
                player instanceof EntityPlayerMP ? serverPacketHandlers.get(channel) : clientPacketHandlers.get(channel)
            )
        ) {
            handler.onPacketData(network, packet, player);
        }
    }

    public void handleRegistrationPacket(Packet250CustomPayload packet, Player player) {
        List<String> channels = extractChannelList(packet);
        for (String channel : channels) {
            activateChannel(player, channel);
        }
    }

    public void handleUnregistrationPacket(Packet250CustomPayload packet, Player player) {
        List<String> channels = extractChannelList(packet);
        for (String channel : channels) {
            deactivateChannel(player, channel);
        }
    }

    public List<String> extractChannelList(Packet250CustomPayload packet) {
        String request = new String(packet.field_73629_c, Charsets.UTF_8);
        return Lists.newArrayList(Splitter.on('\0').split(request));
    }

    @Override
    public Packet3Chat handleChat(NetHandler handler, Packet3Chat chat) {
        Side s = Side.CLIENT;
        if (handler instanceof NetServerHandler) {
            s = Side.SERVER;
        } for (IChatListener listener : chatListeners) {
            chat = s.isClient() ? listener.clientChat(handler, chat) : listener.serverChat(handler, chat);
        }
        return chat;
    }

    @Override
    public void handleTinyPacket(NetHandler handler, Packet131MapData mapData) {
        NetworkModHandler nmh = FMLNetworkHandler.instance().findNetworkModHandler((int)mapData.field_73438_a);
        if (nmh == null) {
            FMLLog.info("Received a tiny packet for network id %d that is not recognised here", mapData.field_73438_a);
            return;
        }
        if (nmh.hasTinyPacketHandler()) {
            nmh.getTinyPacketHandler().handle(handler, mapData);
        } else {
            FMLLog.info("Received a tiny packet for a network mod that does not accept tiny packets %s", nmh.getContainer().getModId());
        }
    }

    @Inject(method = "register", at = @At("HEAD"))
    public void register(ModContainer fmlModContainer, Class<?> clazz, String remoteVersionRange, ASMDataTable asmHarvestedData, CallbackInfo ci) {
        FMLNetworkHandler.instance().registerNetworkMod(fmlModContainer, clazz, asmHarvestedData);
    }
}
