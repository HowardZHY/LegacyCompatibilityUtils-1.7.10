/*
 * Forge Mod Loader
 * Copyright (c) 2012-2013 cpw.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 *
 * Contributors:
 *     cpw - implementation
 */

package cpw.mods.fml.common.modloader;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import space.libs.fml.network.IPacketHandler;
import space.libs.fml.network.Player;

public class ModLoaderPacketHandler implements IPacketHandler {

    public BaseModProxy mod;

    public ModLoaderPacketHandler(BaseModProxy mod) {
        this.mod = mod;
    }

    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
        if (player instanceof EntityPlayerMP) {
            mod.serverCustomPayload(player.getNetServerHandler(), packet);
        } else {
            ModLoaderHelper.sidedHelper.sendClientPacket(mod, packet);
        }
    }

}
