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

import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import space.libs.fml.EntitySpawnPacket;

public interface IModLoaderSidedHelper {

    void finishModLoading(ModLoaderModContainer mc);

    Object getClientGui(BaseModProxy mod, EntityPlayer player, int iD, int x, int y, int z);

    Entity spawnEntity(BaseModProxy mod, EntitySpawnPacket input, EntityRegistry.EntityRegistration registration);

    //void sendClientPacket(BaseModProxy mod, Packet250CustomPayload packet);

    //void clientConnectionOpened(NetHandler netClientHandler, INetworkManager manager, BaseModProxy mod);

    //boolean clientConnectionClosed(INetworkManager manager, BaseModProxy mod);

}
