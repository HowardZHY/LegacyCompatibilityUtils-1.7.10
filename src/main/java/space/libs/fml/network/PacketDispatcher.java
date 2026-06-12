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

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.*;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import space.libs.interfaces.IFMLProxyPacket;
import space.libs.interfaces.IIFMLSidedHandler;

/**
 * A simple utility class to send packet 250 packets around the place
 * @author cpw
 */
@SuppressWarnings("unused")
public class PacketDispatcher {

    public static Packet250CustomPayload getPacket(String type, byte[] data) {
        return new Packet250CustomPayload(type, data);
    }

    public static void sendPacketToServer(Packet packet) {
        validateChannel(packet);
        IIFMLSidedHandler instance = (IIFMLSidedHandler) FMLCommonHandler.instance().getSidedDelegate();
        instance.sendPacket(packet);
    }

    public static void sendPacketToPlayer(Packet packet, Player player) {
        //TODO: Impl might be complicated
        validateChannel(packet);
        if (player instanceof EntityPlayerMP) {
            ((EntityPlayerMP)player).playerNetServerHandler.sendPacket(packet);
        }
    }

    public static void sendPacketToAllAround(double X, double Y, double Z, double range, int dimensionId, Packet packet) {
        validateChannel(packet);
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        if (server != null) {
            server.getConfigurationManager().sendToAllNear(X, Y, Z, range, dimensionId, packet);
        } else {
            FMLLog.fine("Attempt to send packet to all around without a server instance available");
        }
    }

    public static void sendPacketToAllInDimension(Packet packet, int dimId) {
        validateChannel(packet);
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        if (server != null) {
            server.getConfigurationManager().sendPacketToAllPlayersInDimension(packet, dimId);
        } else {
            FMLLog.fine("Attempt to send packet to all in dimension without a server instance available");
        }
    }

    public static void sendPacketToAllPlayers(Packet packet) {
        validateChannel(packet);
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        if (server != null) {
            server.getConfigurationManager().sendPacketToAllPlayers(packet);
        } else {
            FMLLog.fine("Attempt to send packet to all in dimension without a server instance available");
        }
    }

    public static Packet131MapData getTinyPacket(Object mod, short tag, byte[] data) {
        NetworkModHandler nmh = FMLNetworkHandler.instance().findNetworkModHandler(mod);
        return new Packet131MapData((short) nmh.getNetworkId(), tag, data);
    }

    public static void validateChannel(Packet packet) {
        IFMLProxyPacket accessor = (IFMLProxyPacket) packet;
        if (packet instanceof Packet250CustomPayload) {
            Packet250CustomPayload payload = (Packet250CustomPayload) packet;
            if (payload.raw) {
                accessor.setChannel(payload.field_73630_a);
                accessor.setPayload(payload.field_73629_c);
            }
        }
    }
}
