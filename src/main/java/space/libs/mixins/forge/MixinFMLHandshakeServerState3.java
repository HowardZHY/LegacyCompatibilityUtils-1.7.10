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

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.handshake.FMLHandshakeMessage;
import cpw.mods.fml.common.network.handshake.NetworkDispatcher;
import cpw.mods.fml.relauncher.Side;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import space.libs.util.NetworkUtil;
import space.libs.util.forge.GameDataSnapshot;
import space.libs.util.forge.RegistryData;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;

@Mixin(targets = "cpw.mods.fml.common.network.handshake.FMLHandshakeServerState$3", remap = false)
public class MixinFMLHandshakeServerState3 {

    private static Field COMPLETE;

    private static void getField() {
        try {
            Class<?> c = Class.forName("cpw.mods.fml.common.network.handshake.FMLHandshakeServerState");
            Field f = c.getField("COMPLETE");
            f.setAccessible(true);
            COMPLETE = f;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static FMLHandshakeMessage.HandshakeAck HANDSHAKE() {
        try {
            Constructor<FMLHandshakeMessage.HandshakeAck> c = FMLHandshakeMessage.HandshakeAck.class.getDeclaredConstructor(int.class);
            c.setAccessible(true);
            return c.newInstance(2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("all")
    @Inject(method = "accept", at = @At("HEAD"), cancellable = true)
    private void accept(ChannelHandlerContext ctx, FMLHandshakeMessage msg, CallbackInfoReturnable cir) {
        getField();
        try {
            Boolean newFML = ctx.channel().attr(NetworkUtil.FML_MARKER).get();
            if (newFML != null && newFML) {
                System.out.println("WAITINGCACK for 1.8+");
                GameDataSnapshot snapshot = GameDataSnapshot.takeSnapshot();
                Iterator<Map.Entry<String, GameDataSnapshot.Entry>> itr = snapshot.entries.entrySet().iterator();
                while (itr.hasNext()) {
                    Map.Entry<String, GameDataSnapshot.Entry> e = itr.next();
                    ctx.writeAndFlush(new RegistryData(itr.hasNext(), e.getKey(), e.getValue())).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
                }
                ctx.writeAndFlush(HANDSHAKE()).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
                NetworkRegistry.INSTANCE.fireNetworkHandshake(ctx.channel().attr(NetworkDispatcher.FML_DISPATCHER).get(), Side.SERVER);
                cir.setReturnValue(COMPLETE.get(null));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
