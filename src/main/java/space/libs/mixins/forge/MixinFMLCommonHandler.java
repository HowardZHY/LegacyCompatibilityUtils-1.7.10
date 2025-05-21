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
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.libs.fml.*;
import space.libs.interfaces.*;

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
    @Inject(method = "handleServerStarted", at = @At("TAIL"))
    public void handleServerStarted(CallbackInfo ci) {
        this.onWorldLoadTick(MinecraftServer.getServer().worldServers);
    }

    @Dynamic
    @Inject(method = "onPostServerTick", at = @At("TAIL"))
    public void onPostServerTick(CallbackInfo ci) {
        tickEnd(EnumSet.of(TickEvent.Type.SERVER), Side.SERVER);
    }

    @Dynamic
    @Inject(method = "onPreServerTick", at = @At("HEAD"))
    public void onPreServerTick(CallbackInfo ci) {
        this.rescheduleTicks(Side.SERVER);
        tickStart(EnumSet.of(TickEvent.Type.SERVER), Side.SERVER);
    }

    @Dynamic
    @Inject(method = "onPostClientTick", at = @At("TAIL"))
    public void onPostClientTick(CallbackInfo ci) {
        tickEnd(EnumSet.of(TickEvent.Type.CLIENT), Side.CLIENT);
    }

    @Dynamic
    @Inject(method = "onPreClientTick", at = @At("HEAD"))
    public void onPreClientTick(CallbackInfo ci) {
        this.rescheduleTicks(Side.CLIENT);
        tickStart(EnumSet.of(TickEvent.Type.CLIENT), Side.CLIENT);
    }

    @Dynamic
    @Inject(method = "onRenderTickStart", at = @At("HEAD"))
    public void onRenderTickStart(float timer, CallbackInfo ci) {
        tickStart(EnumSet.of(TickEvent.Type.RENDER), Side.CLIENT, timer);
    }

    @Dynamic
    @Inject(method = "onRenderTickEnd", at = @At("TAIL"))
    public void onRenderTickEnd(float timer, CallbackInfo ci) {
        tickEnd(EnumSet.of(TickEvent.Type.RENDER), Side.CLIENT, timer);
    }

    @Dynamic
    @Inject(method = "onPlayerPreTick", at = @At("HEAD"))
    public void onPlayerPreTick(EntityPlayer player, CallbackInfo ci) {
        Side side = player instanceof EntityPlayerMP ? Side.SERVER : Side.CLIENT;
        tickStart(EnumSet.of(TickEvent.Type.PLAYER), side, player);
    }

    @Dynamic
    @Inject(method = "onPlayerPostTick", at = @At("TAIL"))
    public void onPlayerPostTick(EntityPlayer player, CallbackInfo ci) {
        Side side = player instanceof EntityPlayerMP ? Side.SERVER : Side.CLIENT;
        tickEnd(EnumSet.of(TickEvent.Type.PLAYER), side, player);
    }

    @Inject(method = "firePlayerItemPickupEvent", at = @At("HEAD"))
    public void firePlayerItemPickupEvent(EntityPlayer player, EntityItem item, CallbackInfo ci) {
        IGameRegistry.INSTANCE.onPickupNotify(player, item);
    }

    @Inject(method = "firePlayerCraftingEvent", at = @At("HEAD"))
    public void firePlayerCraftingEvent(EntityPlayer player, ItemStack crafted, IInventory craftMatrix, CallbackInfo ci) {
        IGameRegistry.INSTANCE.onItemCraft(player, crafted, craftMatrix);
    }

    @Inject(method = "firePlayerSmeltedEvent", at = @At("HEAD"))
    public void firePlayerSmeltedEvent(EntityPlayer player, ItemStack smelted, CallbackInfo ci) {
        IGameRegistry.INSTANCE.onItemSmelt(player, smelted);
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
        tickEnd(EnumSet.of(TickEvent.Type.WORLD), Side.SERVER, world);
        if (world instanceof World) {
            this.original$onPostWorldTick((World) world);
        }
    }

    @Shadow(prefix = "original$")
    public void original$onPreWorldTick(World world) {}

    public void onPreWorldTick(Object world) {
        tickStart(EnumSet.of(TickEvent.Type.WORLD), Side.SERVER, world);
        if (world instanceof World) {
            this.original$onPreWorldTick((World) world);
        }
    }

    public void onWorldLoadTick(World[] worlds) {
        rescheduleTicks(Side.SERVER);
        try {
            for (World w : worlds) {
                tickStart(EnumSet.of(WORLDLOAD), Side.SERVER, w);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
