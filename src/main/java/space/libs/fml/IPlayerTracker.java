/*
 * Forge Mod Loader
 * Copyright (c) 2012-2013 cpw.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * Contributors:
 *     cpw - implementation
 */

package space.libs.fml;

import net.minecraft.entity.player.EntityPlayer;

@SuppressWarnings("unused")
public interface IPlayerTracker {

    void onPlayerLogin(EntityPlayer player);

    void onPlayerLogout(EntityPlayer player);

    void onPlayerChangedDimension(EntityPlayer player);

    void onPlayerRespawn(EntityPlayer player);

}
