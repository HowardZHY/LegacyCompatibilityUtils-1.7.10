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

import net.minecraft.network.NetServerHandler;

/**
 * @author cpw
 */
public interface Player {

    default NetServerHandler getNetServerHandler() {
        return null;
    }
}
