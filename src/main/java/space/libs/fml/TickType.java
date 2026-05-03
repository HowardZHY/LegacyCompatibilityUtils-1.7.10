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

package space.libs.fml;

import cpw.mods.fml.common.gameevent.TickEvent;
import space.libs.interfaces.IFMLCommonHandler;

import java.util.EnumSet;

@SuppressWarnings("unused")
public enum TickType {

    WORLD,

    RENDER,

    WORLDLOAD,

    CLIENT,

    PLAYER,

    SERVER;

    public EnumSet<TickType> partnerTicks() {
        if (this == CLIENT) return EnumSet.of(RENDER);
        if (this == RENDER) return EnumSet.of(CLIENT);
        return EnumSet.noneOf(TickType.class);
    }

    public static TickType from(TickEvent.Type newType) {
        if (newType == null) {
            return null;
        } else {
            switch (newType.ordinal()) {
                case 0: return WORLD;
                case 1: return PLAYER;
                case 2: return CLIENT;
                case 3: return SERVER;
                case 4: return RENDER;
                case 5: return WORLDLOAD;
                default:
                    return null;
            }
        }
    }

    public static TickEvent.Type to(TickType oldType) {
        if (oldType == null) {
            return null;
        } else {
            switch (oldType.ordinal()) {
                case 0: return TickEvent.Type.WORLD;
                case 1: return TickEvent.Type.RENDER;
                case 2: return IFMLCommonHandler.WORLDLOAD;
                case 3: return TickEvent.Type.CLIENT;
                case 4: return TickEvent.Type.PLAYER;
                case 5: return TickEvent.Type.SERVER;
                default:
                    return null;
            }
        }
    }
}
