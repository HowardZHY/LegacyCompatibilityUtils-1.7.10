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
import cpw.mods.fml.common.gameevent.TickEvent;
import space.libs.fml.ITickHandler;

@SuppressWarnings("unused")
public class BaseModTicker implements ITickHandler {

    public BaseModProxy mod;

    public EnumSet<TickEvent.Type> ticks;

    public boolean clockTickTrigger;

    public boolean sendGuiTicks;

    public BaseModTicker(BaseModProxy mod, boolean guiTicker) {
        this.mod = mod;
        //this.ticks = EnumSet.of(TickEvent.Type.WORLDLOAD);
        this.sendGuiTicks = guiTicker;
    }

    public BaseModTicker(EnumSet<TickEvent.Type> ticks, boolean guiTicker) {
        this.ticks = ticks;
        this.sendGuiTicks = guiTicker;
    }

    @Override
    public void tickStart(EnumSet<TickEvent.Type> types, Object... tickData) {
        tickBaseMod(types, false, tickData);
    }

    @Override
    public void tickEnd(EnumSet<TickEvent.Type> types, Object... tickData) {
        tickBaseMod(types, true, tickData);
    }

    public void tickBaseMod(EnumSet<TickEvent.Type> types, boolean end, Object... tickData) {
        if (FMLCommonHandler.instance().getSide().isClient() && (ticks.contains(TickEvent.Type.CLIENT)
            //|| ticks.contains(TickEvent.Type.WORLDLOAD)
        )) {
            EnumSet<TickEvent.Type> cTypes=EnumSet.copyOf(types);
            if (( end && types.contains(TickEvent.Type.CLIENT))
                //|| types.contains(TickEvent.Type.WORLDLOAD)
            ) {
                clockTickTrigger =  true;
                cTypes.remove(TickEvent.Type.CLIENT);
                //cTypes.remove(TickEvent.Type.WORLDLOAD);
            }
            if (end && clockTickTrigger && types.contains(TickEvent.Type.RENDER)) {
                clockTickTrigger = false;
                cTypes.remove(TickEvent.Type.RENDER);
                cTypes.add(TickEvent.Type.CLIENT);
            }
            sendTick(cTypes, end, tickData);
        } else {
            sendTick(types, end, tickData);
        }
    }

    public void sendTick(EnumSet<TickEvent.Type> types, boolean end, Object... tickData) {
        for (TickEvent.Type type : types) {
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
                //ticks.removeAll(type.partnerTicks());
            }
        }
    }

    @Override
    public EnumSet<TickEvent.Type> ticks() {
        return (clockTickTrigger ? EnumSet.of(TickEvent.Type.RENDER) : ticks);
    }

    @Override
    public String getLabel() {
        return mod.getClass().getSimpleName();
    }

    public void setMod(BaseModProxy mod) {
        this.mod = mod;
    }
}
