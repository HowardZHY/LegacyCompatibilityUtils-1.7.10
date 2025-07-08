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

package space.libs.fml.network;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
//import net.minecraft.network.packet.Packet1Login;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.server.MinecraftServer;

public interface IConnectionHandler {
    /**
     * Called when a player logs into the server.
     * SERVER SIDE
     */
    void playerLoggedIn(Player player, NetHandler netHandler, INetworkManager manager);

    /**
     * If you don't want the connection to continue, return a non-empty string here
     * If you do, you can do other stuff here - note no FML negotiation has occurred yet
     * though the client is verified as having FML installed
     * SERVER SIDE
     */
    String connectionReceived(NetLoginHandler netHandler, INetworkManager manager);

    /**
     * Fired when a remote connection is opened
     * CLIENT SIDE
     *
     */
    void connectionOpened(NetHandler netClientHandler, String server, int port, INetworkManager manager);

    /**
     * Fired when a local connection is opened
     * CLIENT SIDE
     *
     */
    void connectionOpened(NetHandler netClientHandler, MinecraftServer server, INetworkManager manager);

    /**
     * Fired when a connection closes
     * ALL SIDES
     *
     */
    void connectionClosed(INetworkManager manager);

    /**
     * Fired when the client established the connection to the server
     * CLIENT SIDE
     */
    void clientLoggedIn(NetHandler clientHandler, INetworkManager manager, Packet1Login login);

}
