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

import java.util.EnumSet;

/**
 * Tick handler for mods to implement and register through the TickRegistry
 * <p>
 * The data available to each tick is documented in the TickType
 *
 * @author cpw
 */
public interface ITickHandler {

    /**
     * Called at the "start" phase of a tick
     * Multiple ticks may fire simultaneously- you will only be called once with all the firing ticks
     */
    void tickStart(EnumSet<TickEvent.Type> type, Object... tickData);

    /**
     * Called at the "end" phase of a tick
     * Multiple ticks may fire simultaneously- you will only be called once with all the firing ticks
     */
    void tickEnd(EnumSet<TickEvent.Type> type, Object... tickData);

    /**
     * Returns the list of ticks this tick handler is interested in receiving at the minute
     */
    EnumSet<TickEvent.Type> ticks();

    /**
     * A profiling label for this tick handler
     */
    String getLabel();

}
