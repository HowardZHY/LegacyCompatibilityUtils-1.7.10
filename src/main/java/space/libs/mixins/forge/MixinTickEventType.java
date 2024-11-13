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
package space.libs.mixins.forge;

import cpw.mods.fml.common.gameevent.TickEvent;
import org.spongepowered.asm.mixin.Mixin;

import java.util.EnumSet;

import static cpw.mods.fml.common.gameevent.TickEvent.Type.*;

@SuppressWarnings("all")
@Mixin(value = TickEvent.Type.class, remap = false)
public class MixinTickEventType {

    public EnumSet<TickEvent.Type> partnerTicks() {
        if ((TickEvent.Type) (Object) this == CLIENT) return EnumSet.of(RENDER);
        if ((TickEvent.Type) (Object) this == RENDER) return EnumSet.of(CLIENT);
        return EnumSet.noneOf(TickEvent.Type.class);
    }
}
