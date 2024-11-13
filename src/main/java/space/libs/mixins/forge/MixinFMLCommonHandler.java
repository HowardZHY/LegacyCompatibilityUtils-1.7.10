/*
 * Forge Mod Loader
 * Copyright (c) 2012-2013 cpw.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * https://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 */
package space.libs.mixins.forge;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.EventBus;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.libs.interfaces.IFMLCommonHandler;
import space.libs.interfaces.IScheduledTickHandler;
import space.libs.util.TickRegistry;

import java.util.EnumSet;
import java.util.List;

@SuppressWarnings("unused")
@Mixin(value = FMLCommonHandler.class, remap = false)
public abstract class MixinFMLCommonHandler implements IFMLCommonHandler {

    @Shadow
    public abstract EventBus bus();

    public List<IScheduledTickHandler> scheduledClientTicks = Lists.newArrayList();

    public List<IScheduledTickHandler> scheduledServerTicks = Lists.newArrayList();

    @Dynamic
    @Inject(method = "onPreClientTick", at = @At("HEAD"))
    public void onPreClientTick(CallbackInfo ci) {
        this.rescheduleTicks(Side.CLIENT);
    }

    @Dynamic
    @Inject(method = "handleServerStarted", at = @At("TAIL"))
    public void handleServerStarted(CallbackInfo ci) {
        this.onWorldLoadTick(MinecraftServer.getServer().worldServers);
    }

    @Dynamic
    @Inject(method = "onPreServerTick", at = @At("HEAD"))
    public void onPreServerTick(CallbackInfo ci) {
        this.rescheduleTicks(Side.SERVER);
    }

    public void rescheduleTicks(Side side) {
        TickRegistry.updateTickQueue(side.isClient() ? scheduledClientTicks : scheduledServerTicks, side);
    }

    public void tickStart(EnumSet<TickEvent.Type> ticks, Side side, Object ... data) {
        List<IScheduledTickHandler> scheduledTicks = side.isClient() ? scheduledClientTicks : scheduledServerTicks;
        if (scheduledTicks.size()==0) {
            return;
        }
        for (IScheduledTickHandler ticker : scheduledTicks) {
            EnumSet<TickEvent.Type> ticksToRun = EnumSet.copyOf(Objects.firstNonNull(ticker.ticks(), EnumSet.noneOf(TickEvent.Type.class)));
            ticksToRun.retainAll(ticks);
            if (!ticksToRun.isEmpty()) {
                ticker.tickStart(ticksToRun, data);
            }
        }
    }

    public void tickEnd(EnumSet<TickEvent.Type> ticks, Side side, Object ... data) {
        List<IScheduledTickHandler> scheduledTicks = side.isClient() ? scheduledClientTicks : scheduledServerTicks;
        if (scheduledTicks.size()==0) {
            return;
        }
        for (IScheduledTickHandler ticker : scheduledTicks) {
            EnumSet<TickEvent.Type> ticksToRun = EnumSet.copyOf(Objects.firstNonNull(ticker.ticks(), EnumSet.noneOf(TickEvent.Type.class)));
            ticksToRun.retainAll(ticks);
            if (!ticksToRun.isEmpty()) {
                ticker.tickEnd(ticksToRun, data);
            }
        }
    }

    @Shadow(prefix = "original$")
    public void original$onPostWorldTick(World world) {}

    public void onPostWorldTick(Object world) {
        if (world instanceof World) {
            this.original$onPostWorldTick((World) world);
        }
    }

    @Shadow(prefix = "original$")
    public void original$onPreWorldTick(World world) {}

    public void onPreWorldTick(Object world) {
        if (world instanceof World) {
            this.original$onPreWorldTick((World) world);
        }
    }

    public void onWorldLoadTick(World[] worlds) {
        rescheduleTicks(Side.SERVER);
        for (World w : worlds) {
            bus().post(new WorldEvent.Load(w));
        }
    }
}
