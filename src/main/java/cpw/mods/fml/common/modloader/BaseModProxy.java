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

package cpw.mods.fml.common.modloader;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetServerHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;
import space.libs.fml.TickType;

import java.util.Random;

@SuppressWarnings("unused")
public interface BaseModProxy {

    void modsLoaded();

    void load();

    String getName();

    String getPriorities();

    String getVersion();

    boolean doTickInGUI(TickType type, boolean end, Object... tickData);

    boolean doTickInGame(TickType type, boolean end, Object... tickData);

    void generateSurface(World w, Random random, int i, int j);

    void generateNether(World w, Random random, int i, int j);

    int addFuel(int itemId, int damage);

    void takenFromCrafting(EntityPlayer player, ItemStack item, IInventory craftMatrix);

    void takenFromFurnace(EntityPlayer player, ItemStack item);

    void onClientLogout(INetworkManager manager);

    void onClientLogin(EntityPlayer player);

    void serverDisconnect();

    void serverConnect(NetHandler handler);

    void receiveCustomPacket(Packet250CustomPayload packet);

    void clientChat(String text);

    void onItemPickup(EntityPlayer player, ItemStack item);

    void serverCustomPayload(NetServerHandler handler, Packet250CustomPayload packet);

    void serverChat(NetServerHandler source, String message);
}
