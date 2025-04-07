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

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.handshake.NetworkDispatcher;
import cpw.mods.fml.common.network.internal.FMLMessage;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.AttributeKey;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.*;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.server.management.ServerConfigurationManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import space.libs.CompatLib;
import space.libs.interfaces.IFMLProxyPacket;
import space.libs.interfaces.INetworkDispatcher;
import space.libs.util.NetworkUtil;
import space.libs.util.cursedmixinextensions.annotations.Public;
import space.libs.util.forge.MultiPartCustomPayload;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Mixin(value = NetworkDispatcher.class, remap = false)
public abstract class MixinNetworkDispatcher extends SimpleChannelInboundHandler<Packet> implements INetworkDispatcher {

    private static Field VANILLA;

    private static Field STATE;

    @Shadow
    public @Final NetworkManager manager;

    @Shadow
    private @Final ServerConfigurationManager scm;

    @Shadow
    private EntityPlayerMP player;

    @Shadow
    private @Final EmbeddedChannel handshakeChannel;

    @Shadow
    private NetHandlerPlayServer serverHandler;

    @Shadow
    private INetHandler netHandler;

    private Map<String, String> modList;

    public void setModList(Map<String, String> modList) {
        this.modList = modList;
    }

    public Map<String, String> getModList() {
        return Collections.unmodifiableMap(modList);
    }

    private MultiPartCustomPayload multipart = null;

    @Shadow
    private void kickWithMessage(String message) {}

    @Shadow
    abstract int serverInitiateHandshake();

    @WrapWithCondition(
        method = "serverInitiateHandshake",
        at = @At(
            value = "INVOKE",
            target = "Lio/netty/channel/ChannelPipeline;addFirst(Ljava/lang/String;Lio/netty/channel/ChannelHandler;)Lio/netty/channel/ChannelPipeline;",
            ordinal = 0
        )
    )
    private boolean serverInitiateHandshake(ChannelPipeline pipeline, String name, ChannelHandler handler) {
        try {
            pipeline.addFirst(name, handler);
        } catch (Exception ignored) {}
        return false;
    }

    private void SetState() {
        try {
            Field state = NetworkDispatcher.class.getDeclaredField("state");
            state.setAccessible(true);
            state.set(this, STATE.get(null));
        } catch (Exception e) {
            FMLLog.severe("Cannot set State", e);
        }
    }

    private void CallCompleteServerSideConnection() {
        try {
            Method m = NetworkDispatcher.class.getDeclaredMethod("completeServerSideConnection", VANILLA.getType());
            m.setAccessible(true);
            m.invoke(this, VANILLA.get(null));
        } catch (Exception e) {
            FMLLog.severe("Cannot invoke completeServerSideConnection", e);
        }
    }

    @Inject(method = "serverToClientHandshake", at = @At("RETURN"))
    public void serverToClientHandshake(EntityPlayerMP player, CallbackInfo ci) {
        try {
            Boolean newFML = this.manager.channel().attr(NetworkUtil.FML_MARKER).get();
            System.out.println(newFML);
            if (newFML != null && newFML) {
                FMLLog.info("Connection received with FML.");
            } else {
                serverInitiateHandshake();
                FMLLog.info("Connection received without FML marker, assuming vanilla.");
                this.CallCompleteServerSideConnection();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Inject(method = "kickVanilla", at = @At("HEAD"), cancellable = true)
    private void kickVanilla(CallbackInfo ci) {
        //serverInitiateHandshake();
        //this.CallCompleteServerSideConnection();
        //ci.cancel();
    }

    @WrapWithCondition(method = "write",
        at = @At(
            value = "INVOKE",
            target = "Lio/netty/channel/ChannelHandlerContext;write(Ljava/lang/Object;Lio/netty/channel/ChannelPromise;)Lio/netty/channel/ChannelFuture;",
            ordinal = 1
        )
    )
    private boolean write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise, ChannelHandlerContext ctx1, Object msg1, ChannelPromise promise1) throws IOException {
        Boolean newFML = ctx.channel().attr(NetworkUtil.FML_MARKER).get();
        if (newFML != null && newFML) {
            System.out.println("Write for 1.8+");
            IFMLProxyPacket accessor = (IFMLProxyPacket) msg1;
            List<Packet> parts = (accessor.toS3FPackets());
            for (Packet pkt : parts) {
                ctx.write(pkt, promise);
            }
            return false;
        }
        return true;
    }

    @SuppressWarnings("all")
    @Inject(method = "handleClientSideCustomPacket", at = @At("HEAD"), cancellable = true)
    private void handleClientSideCustomPacket(S3FPacketCustomPayload msg, ChannelHandlerContext context, CallbackInfoReturnable<Boolean> cir) {
        NetworkDispatcher This = (NetworkDispatcher) (Object) this;
        if ("FML|MP".equals(msg.func_149169_c())) {
            try {
                if (multipart == null) {
                    multipart = new MultiPartCustomPayload(new PacketBuffer(Unpooled.wrappedBuffer(msg.func_149168_d())));
                } else {
                    multipart.processPart(new PacketBuffer(Unpooled.wrappedBuffer(msg.func_149168_d())));
                }
            } catch (IOException e) {
                this.kickWithMessage(e.getMessage());
                multipart = null;
                cir.setReturnValue(Boolean.TRUE);
            }
            if (multipart.isComplete()) {
                msg = multipart;
                multipart = null;
            } else {
                cir.setReturnValue(Boolean.TRUE);
            }
        }
    }

    @Inject(method = "<clinit>", at = @At("RETURN"))
    private static void clinit(CallbackInfo ci) {
        try {
            Class<?> c = Class.forName("cpw.mods.fml.common.network.handshake.NetworkDispatcher$ConnectionType");
            Field f = c.getDeclaredField("VANILLA");
            f.setAccessible(true);
            VANILLA = f;
            Class<?> c1 = Class.forName("cpw.mods.fml.common.network.handshake.NetworkDispatcher$ConnectionState");
            Field f1 = c1.getDeclaredField("AWAITING_HANDSHAKE");
            f.setAccessible(true);
            STATE = f1;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
