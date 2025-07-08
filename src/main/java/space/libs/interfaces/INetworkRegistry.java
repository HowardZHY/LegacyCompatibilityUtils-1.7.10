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
package space.libs.interfaces;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.NetServerHandler;
import net.minecraft.network.packet.*;
import net.minecraft.server.MinecraftServer;
import space.libs.fml.network.IChatListener;
import space.libs.fml.network.IConnectionHandler;
import space.libs.fml.network.IPacketHandler;
import space.libs.fml.network.Player;

@SuppressWarnings("unused")
public interface INetworkRegistry {

    static INetworkRegistry instance() {
        return (INetworkRegistry) (Object) NetworkRegistry.INSTANCE;
    }

    static void RegisterChannel(IPacketHandler handler, String channelName) {
        instance().registerChannel(handler, channelName);
    }

    static void RegisterChannel(IPacketHandler handler, String channelName, Side side) {
        instance().registerChannel(handler, channelName, side);
    }

    static void RegisterConnectionHandler(IConnectionHandler handler) {
        instance().registerConnectionHandler(handler);
    }

    static void RegisterChatListener(IChatListener listener) {
        instance().registerChatListener(listener);
    }

    void registerChannel(IPacketHandler handler, String channelName);

    void registerChannel(IPacketHandler handler, String channelName, Side side);

    void registerConnectionHandler(IConnectionHandler handler);

    void registerChatListener(IChatListener listener);

    void playerLoggedIn(EntityPlayerMP player, NetServerHandler netHandler, INetworkManager manager);

    String connectionReceived(NetLoginHandler netHandler, INetworkManager manager);

    void connectionOpened(NetHandler netHandler, String server, int port, INetworkManager networkManager);

    void connectionOpened(NetHandler netHandler, MinecraftServer server, INetworkManager networkManager);

    void clientLoggedIn(NetHandler clientHandler, INetworkManager manager, Packet1Login login);

    void connectionClosed(INetworkManager manager, EntityPlayer player);

    void handleCustomPacket(Packet250CustomPayload packet, INetworkManager network, NetHandler handler);

    void handlePacket(Packet250CustomPayload packet, INetworkManager network, Player player);

    Packet3Chat handleChat(NetHandler handler, Packet3Chat chat);

    void handleTinyPacket(NetHandler handler, Packet131MapData mapData);

}
