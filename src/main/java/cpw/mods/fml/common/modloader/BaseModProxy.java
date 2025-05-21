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

import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

@SuppressWarnings("unused")
public interface BaseModProxy {

    void modsLoaded();

    void load();

    String getName();

    String getPriorities();

    String getVersion();

    boolean doTickInGUI(TickEvent.Type type, boolean end, Object... tickData);

    boolean doTickInGame(TickEvent.Type type, boolean end, Object... tickData);

    void generateSurface(World w, Random random, int i, int j);

    void generateNether(World w, Random random, int i, int j);

    int addFuel(int itemId, int damage);

    void takenFromCrafting(EntityPlayer player, ItemStack item, IInventory craftMatrix);

    void takenFromFurnace(EntityPlayer player, ItemStack item);

    //public abstract void onClientLogout(INetworkManager manager);

    void onClientLogin(EntityPlayer player);

    void serverDisconnect();

    //public abstract void serverConnect(NetHandler handler);

    //public abstract void receiveCustomPacket(Packet250CustomPayload packet);

    void clientChat(String text);

    void onItemPickup(EntityPlayer player, ItemStack item);

    //public abstract void serverCustomPayload(NetServerHandler handler, Packet250CustomPayload packet);

    //public abstract void serverChat(NetServerHandler source, String message);
}
