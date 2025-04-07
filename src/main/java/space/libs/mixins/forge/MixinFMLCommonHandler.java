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
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.IFMLSidedHandler;
import cpw.mods.fml.common.eventhandler.EventBus;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import space.libs.interfaces.IFMLCommonHandler;
import space.libs.interfaces.IHandshake;
import space.libs.interfaces.IScheduledTickHandler;
import space.libs.util.NetworkUtil;
import space.libs.util.cursedmixinextensions.annotations.Public;
import space.libs.util.forge.TickRegistry;

import java.util.EnumSet;
import java.util.List;

@SuppressWarnings("unused")
@Mixin(value = FMLCommonHandler.class, remap = false)
public abstract class MixinFMLCommonHandler implements IFMLCommonHandler {

    @Shadow
    private IFMLSidedHandler sidedDelegate;

    @Shadow
    public abstract EventBus bus();

    @Shadow
    public abstract boolean shouldAllowPlayerLogins();

    public List<IScheduledTickHandler> scheduledClientTicks = Lists.newArrayList();

    public List<IScheduledTickHandler> scheduledServerTicks = Lists.newArrayList();

    @Public
    private static TickEvent.Type WORLDLOAD;

    @Dynamic
    @Inject(method = "shouldAllowPlayerLogins", at = @At("HEAD"), cancellable = true)
    public void setAllowPlayerLogins(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(Boolean.TRUE);
    }

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
            WORLDLOAD = addEnum();
            for (World w : worlds) {
                tickStart(EnumSet.of(WORLDLOAD), Side.SERVER, w);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Public
    private static TickEvent.Type addEnum() {
        return EnumHelper.addEnum(TickEvent.Type.class, "WORLDLOAD", new Class[0], new Object[0]);
    }

    public boolean handleServerHandshake(C00Handshake packet, NetworkManager manager) {
        if (!this.sidedDelegate.shouldAllowPlayerLogins()) {
            ChatComponentText text = new ChatComponentText("Server is still starting! Please wait before reconnecting.");
            FMLLog.info("Disconnecting Player: " + text.getUnformattedText());
            manager.scheduleOutboundPacket(new S00PacketDisconnect(text));
            manager.closeChannel(text);
            return false;
        }
        IHandshake accessor = (IHandshake) packet;
        if (packet.func_149594_c() == EnumConnectionState.LOGIN && (!NetworkRegistry.INSTANCE.isVanillaAccepted(Side.CLIENT) && !accessor.hasFMLMarker())) {
            manager.setConnectionState(EnumConnectionState.LOGIN);
            ChatComponentText text = new ChatComponentText("This server requires Forge to be installed. Contact your server admin for more details.");
            FMLLog.info("Disconnecting Player: " + text.getUnformattedText());
            manager.scheduleOutboundPacket(new S00PacketDisconnect(text));
            manager.closeChannel(text);
            return false;
        }
        manager.channel().attr(NetworkUtil.FML_MARKER).set(accessor.hasFMLMarker());
        return true;
    }
}
