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

package space.libs.fml.network;

import java.lang.reflect.Method;
import java.util.Set;

import com.google.common.base.Strings;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.discovery.ASMDataTable;
import cpw.mods.fml.common.discovery.ASMDataTable.ASMData;
import cpw.mods.fml.common.network.FMLNetworkException;
import cpw.mods.fml.common.versioning.InvalidVersionSpecificationException;
import cpw.mods.fml.common.versioning.VersionRange;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import space.libs.interfaces.INetworkRegistry;

@SuppressWarnings("unused")
public class NetworkModHandler {

    public static Object connectionHandlerDefaultValue;

    public static Object packetHandlerDefaultValue;

    public static Object clientHandlerDefaultValue;

    public static Object serverHandlerDefaultValue;

    public static Object tinyPacketHandlerDefaultValue;

    public static int assignedIds = 1;

    public int localId;

    public int networkId;

    public ModContainer container;

    public NetworkMod mod;

    public Method checkHandler;

    public VersionRange acceptableRange;

    public ITinyPacketHandler tinyPacketHandler;

    public NetworkModHandler(ModContainer container, NetworkMod modAnnotation) {
        this.container = container;
        this.mod = modAnnotation;
        this.localId = assignedIds++;
        this.networkId = this.localId;
        // Skip over the map object because it has special network id meaning
        if (Item.getIdFromItem(Items.map) == assignedIds) {
            assignedIds++;
        }
    }

    public NetworkModHandler(ModContainer container, Class<?> networkModClass, ASMDataTable table) {
        this(container, networkModClass.getAnnotation(NetworkMod.class));
        if (this.mod == null) {
            return;
        }
        Set<ASMData> versionCheckHandlers = table.getAnnotationsFor(container).get(NetworkMod.VersionCheckHandler.class.getName());
        String versionCheckHandlerMethod = null;
        for (ASMData vch : versionCheckHandlers) {
            if (vch.getClassName().equals(networkModClass.getName())) {
                versionCheckHandlerMethod = vch.getObjectName();
                versionCheckHandlerMethod = versionCheckHandlerMethod.substring(0,versionCheckHandlerMethod.indexOf('('));
                break;
            }
        }
        if (versionCheckHandlerMethod != null) {
            try {
                Method checkHandlerMethod = networkModClass.getDeclaredMethod(versionCheckHandlerMethod, String.class);
                if (checkHandlerMethod.isAnnotationPresent(NetworkMod.VersionCheckHandler.class)) {
                    this.checkHandler = checkHandlerMethod;
                }
            } catch (Exception e) {
                FMLLog.warning("The declared version check handler method %s on network mod id %s is not accessible", versionCheckHandlerMethod, container.getModId());
            }
        }
        configureNetworkMod(container);
    }

    public void configureNetworkMod(ModContainer container) {
        if (this.checkHandler == null) {
            String versionBounds = mod.versionBounds();
            if (!Strings.isNullOrEmpty(versionBounds)) {
                try {
                    this.acceptableRange = VersionRange.createFromVersionSpec(versionBounds);
                } catch (InvalidVersionSpecificationException e) {
                    FMLLog.warning("Invalid bounded range %s specified for network mod id %s", versionBounds, container.getModId());
                }
            }
        }
        FMLLog.finer("Testing mod %s to verify it accepts its own version in a remote connection", container.getModId());
        boolean acceptsSelf = acceptVersion(container.getVersion());
        if (!acceptsSelf) {
            FMLLog.severe("The mod %s appears to reject its own version number (%s) in its version handling. This is likely a severe bug in the mod!", container.getModId(), container.getVersion());
        } else {
            FMLLog.finer("The mod %s accepts its own version (%s)", container.getModId(), container.getVersion());
        }
        tryCreatingPacketHandler(container, mod.packetHandler(), mod.channels(), null);
        if (FMLCommonHandler.instance().getSide().isClient()) {
            if (mod.clientPacketHandlerSpec() != getClientHandlerSpecDefaultValue()) {
                tryCreatingPacketHandler(container, mod.clientPacketHandlerSpec().packetHandler(), mod.clientPacketHandlerSpec().channels(), Side.CLIENT);
            }
        }
        if (mod.serverPacketHandlerSpec() != getServerHandlerSpecDefaultValue()) {
            tryCreatingPacketHandler(container, mod.serverPacketHandlerSpec().packetHandler(), mod.serverPacketHandlerSpec().channels(), Side.SERVER);
        }
        if (mod.connectionHandler() != getConnectionHandlerDefaultValue()) {
            IConnectionHandler instance;
            try {
                instance = mod.connectionHandler().newInstance();
            } catch (Exception e) {
                FMLLog.severe("Unable to create connection handler instance %s", mod.connectionHandler().getName());
                throw new FMLNetworkException(e);
            }
            INetworkRegistry.RegisterConnectionHandler(instance);
        }
        if (mod.tinyPacketHandler()!=getTinyPacketHandlerDefaultValue()) {
            try {
                tinyPacketHandler = mod.tinyPacketHandler().newInstance();
            } catch (Exception e) {
                FMLLog.severe("Unable to create tiny packet handler instance %s", mod.tinyPacketHandler().getName());
                throw new FMLNetworkException(e);
            }
        }
    }

    public void tryCreatingPacketHandler(ModContainer container, Class<? extends IPacketHandler> clazz, String[] channels, Side side) {
        if (side!=null && side.isClient() && ! FMLCommonHandler.instance().getSide().isClient()) {
            return;
        }
        if (clazz!=getPacketHandlerDefaultValue()) {
            if (channels.length==0) {
                FMLLog.warning("The mod id %s attempted to register a packet handler without specifying channels for it", container.getModId());
            } else {
                IPacketHandler instance;
                try {
                    instance = clazz.newInstance();
                } catch (Exception e) {
                    FMLLog.severe("Unable to create a packet handler instance %s for mod %s", clazz.getName(), container.getModId());
                    throw new FMLNetworkException(e);
                }
                for (String channel : channels) {
                    INetworkRegistry.RegisterChannel(instance, channel, side);
                }
            }
        } else if (channels.length > 0) {
            FMLLog.warning("The mod id %s attempted to register channels without specifying a packet handler", container.getModId());
        }
    }

    /**
     * @return the default {@link NetworkMod#connectionHandler()} annotation value
     */
    public Object getConnectionHandlerDefaultValue() {
        try {
            if (connectionHandlerDefaultValue == null) {
                connectionHandlerDefaultValue = NetworkMod.class.getMethod("connectionHandler").getDefaultValue();
            }
            return connectionHandlerDefaultValue;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Derp?", e);
        }
    }

    /**
     * @return the default {@link NetworkMod#packetHandler()} annotation value
     */
    public Object getPacketHandlerDefaultValue() {
        try {
            if (packetHandlerDefaultValue == null) {
                packetHandlerDefaultValue = NetworkMod.class.getMethod("packetHandler").getDefaultValue();
            }
            return packetHandlerDefaultValue;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Derp?", e);
        }
    }

    /**
     * @return the default {@link NetworkMod#tinyPacketHandler()} annotation value
     */
    public Object getTinyPacketHandlerDefaultValue() {
        try {
            if (tinyPacketHandlerDefaultValue == null) {
                tinyPacketHandlerDefaultValue = NetworkMod.class.getMethod("tinyPacketHandler").getDefaultValue();
            }
            return tinyPacketHandlerDefaultValue;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Derp?", e);
        }
    }

    /**
     * @return the {@link NetworkMod#clientPacketHandlerSpec()} default annotation value
     */
    public Object getClientHandlerSpecDefaultValue() {
        try {
            if (clientHandlerDefaultValue == null) {
                clientHandlerDefaultValue = NetworkMod.class.getMethod("clientPacketHandlerSpec").getDefaultValue();
            }
            return clientHandlerDefaultValue;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Derp?", e);
        }
    }

    /**
     * @return the default {@link NetworkMod#serverPacketHandlerSpec()} annotation value
     */
    public Object getServerHandlerSpecDefaultValue() {
        try {
            if (serverHandlerDefaultValue == null) {
                serverHandlerDefaultValue = NetworkMod.class.getMethod("serverPacketHandlerSpec").getDefaultValue();
            }
            return serverHandlerDefaultValue;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Derp?", e);
        }
    }

    public boolean requiresClientSide() {
        return mod.clientSideRequired();
    }

    public boolean requiresServerSide() {
        return mod.serverSideRequired();
    }

    public boolean acceptVersion(String version) {
        return true;
    }

    public int getLocalId() {
        return localId;
    }

    public int getNetworkId() {
        return networkId;
    }

    public ModContainer getContainer() {
        return container;
    }

    public NetworkMod getMod() {
        return mod;
    }

    public boolean isNetworkMod() {
        return mod != null;
    }

    public void setNetworkId(int value) {
        this.networkId = value;
    }

    public boolean hasTinyPacketHandler() {
        return tinyPacketHandler != null;
    }

    public ITinyPacketHandler getTinyPacketHandler() {
        return tinyPacketHandler;
    }
}
