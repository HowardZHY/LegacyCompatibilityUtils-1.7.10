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

import cpw.mods.fml.common.gameevent.TickEvent;
import space.libs.util.cursedmixinextensions.annotations.Public;

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
        if (this==CLIENT) return EnumSet.of(RENDER);
        if (this==RENDER) return EnumSet.of(CLIENT);
        return EnumSet.noneOf(TickType.class);
    }
}
