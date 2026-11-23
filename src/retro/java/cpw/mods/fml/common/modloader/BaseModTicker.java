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

import java.util.EnumSet;

import cpw.mods.fml.common.FMLCommonHandler;
import space.libs.fml.ITickHandler;
import space.libs.fml.TickType;

public class BaseModTicker implements ITickHandler {

    public BaseModProxy mod;

    public EnumSet<TickType> ticks;

    public boolean clockTickTrigger;

    public boolean sendGuiTicks;

    public BaseModTicker(BaseModProxy mod, boolean guiTicker) {
        this.mod = mod;
        this.ticks = EnumSet.of(TickType.WORLDLOAD);
        this.sendGuiTicks = guiTicker;
    }

    public BaseModTicker(EnumSet<TickType> ticks, boolean guiTicker) {
        this.ticks = ticks;
        this.sendGuiTicks = guiTicker;
    }

    @Override
    public void tickStart(EnumSet<TickType> types, Object... tickData) {
        tickBaseMod(types, false, tickData);
    }

    @Override
    public void tickEnd(EnumSet<TickType> types, Object... tickData) {
        tickBaseMod(types, true, tickData);
    }

    public void tickBaseMod(EnumSet<TickType> types, boolean end, Object... tickData) {
        if (FMLCommonHandler.instance().getSide().isClient() && (ticks.contains(TickType.CLIENT) || ticks.contains(TickType.WORLDLOAD))) {
            EnumSet<TickType> cTypes=EnumSet.copyOf(types);
            if ((end && types.contains(TickType.CLIENT)) || types.contains(TickType.WORLDLOAD)) {
                clockTickTrigger =  true;
                cTypes.remove(TickType.CLIENT);
                cTypes.remove(TickType.WORLDLOAD);
            }
            if (end && clockTickTrigger && types.contains(TickType.RENDER)) {
                clockTickTrigger = false;
                cTypes.remove(TickType.RENDER);
                cTypes.add(TickType.CLIENT);
            }
            sendTick(cTypes, end, tickData);
        } else {
            sendTick(types, end, tickData);
        }
    }

    @SuppressWarnings("UnusedAssignment")
    public void sendTick(EnumSet<TickType> types, boolean end, Object... tickData) {
        for (TickType type : types) {
            if (!ticks.contains(type)) {
                continue;
            }
            boolean keepTicking = true;
            if (sendGuiTicks) {
                keepTicking = mod.doTickInGUI(type, end, tickData);
            } else {
                keepTicking = mod.doTickInGame(type, end, tickData);
            }
            if (!keepTicking) {
                ticks.remove(type);
                ticks.removeAll(type.partnerTicks());
            }
        }
    }

    @Override
    public EnumSet<TickType> ticks() {
        return (clockTickTrigger ? EnumSet.of(TickType.RENDER) : ticks);
    }

    @Override
    public String getLabel() {
        return mod.getClass().getSimpleName();
    }

    public void setMod(BaseModProxy mod) {
        this.mod = mod;
    }
}
