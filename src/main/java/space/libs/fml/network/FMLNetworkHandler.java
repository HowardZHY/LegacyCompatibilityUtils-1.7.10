package space.libs.fml.network;

import com.google.common.collect.*;
import com.google.common.hash.Hashing;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.discovery.ASMDataTable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.*;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.world.World;
import space.libs.CompatLib;
import space.libs.interfaces.*;

import java.io.IOException;
import java.net.*;
import java.nio.charset.Charset;
import java.util.*;

@SuppressWarnings({"UnstableApiUsage", "unused"})
public class FMLNetworkHandler extends cpw.mods.fml.common.network.internal.FMLNetworkHandler {

    public static final int FML_HASH = Hashing.murmur3_32().hashString("FML", Charset.defaultCharset()).asInt();

    public static final int PROTOCOL_VERSION = 0x2;

    public static final FMLNetworkHandler INSTANCE = new FMLNetworkHandler();

    public static final int LOGIN_RECEIVED = 1;

    public static final int CONNECTION_VALID = 2;

    public static final int FML_OUT_OF_DATE = -1;

    public static final int MISSING_MODS_OR_VERSIONS = -2;

    public Map<NetLoginHandler, Integer> loginStates = Maps.newHashMap();

    public Map<ModContainer, NetworkModHandler> networkModHandlers = Maps.newHashMap();

    public Map<Integer, NetworkModHandler> networkIdLookup = Maps.newHashMap();

    public FMLNetworkHandler() {
        super();
    }

    public static FMLNetworkHandler instance() {
        return INSTANCE;
    }

    public static void handlePacket250Packet(Packet250CustomPayload packet, INetworkManager network, NetHandler handler) {
        String target = packet.field_73630_a;
        if (target != null && target.startsWith("MC|")) {
            handler.handleVanilla250Packet(packet);
        }
        if ("FML".equals(target)) {
            instance().handleFMLPacket(packet, network, handler);
        } else {
            INetworkRegistry.instance().handleCustomPacket(packet, network, handler);
        }
    }

    public static void onConnectionEstablishedToServer(NetHandler clientHandler, INetworkManager manager, Packet1Login login) {
        INetworkRegistry.instance().clientLoggedIn(clientHandler, manager, login);
    }

    public void handleFMLPacket(Packet250CustomPayload packet, INetworkManager network, NetHandler netHandler) {
        // NO-OP
    }

    public static void onConnectionReceivedFromClient(NetLoginHandler netLoginHandler, MinecraftServer server, SocketAddress address, String userName) {
        instance().handleClientConnection(netLoginHandler, server, address, userName);
    }

    public void handleClientConnection(NetLoginHandler netLoginHandler, MinecraftServer server, SocketAddress address, String userName) {
        String modKick = INetworkRegistry.instance().connectionReceived(netLoginHandler, netLoginHandler.getNetworkManager());
        CompatLib.LOGGER.warn("Custom Legacy ModKick: " + modKick);
    }

    public boolean handleVanillaLoginKick(NetLoginHandler netLoginHandler, MinecraftServer server, SocketAddress address, String userName) {
        ServerConfigurationManager playerList = server.getConfigurationManager();
        String kickReason = playerList.allowUserToConnect(address, playerList.getPlayerByUsername(userName).getGameProfile());
        return kickReason == null;
    }

    public static void handleLoginPacketOnServer(NetLoginHandler handler, Packet1Login login) {}

    public static void setHandlerState(NetLoginHandler handler, int state) {
        instance().loginStates.put(handler, state);
    }

    public static Packet1Login getFMLFakeLoginPacket() {
        throw new UnsupportedOperationException();
    }

    public Packet250CustomPayload getModListRequestPacket() {
        //return PacketDispatcher.getPacket("FML", FMLPacket.makePacket(MOD_LIST_REQUEST));
        throw new UnsupportedOperationException();
    }

    public void registerNetworkMod(NetworkModHandler handler) {
        networkModHandlers.put(handler.getContainer(), handler);
        networkIdLookup.put(handler.getNetworkId(), handler);
    }

    public boolean registerNetworkMod(ModContainer container, Class<?> networkModClass, ASMDataTable asmData) {
        NetworkModHandler handler = new NetworkModHandler(container, networkModClass, asmData);
        if (handler.isNetworkMod()) {
            CompatLib.LOGGER.info("Registering Legacy NetworkMod" + networkModClass);
            this.registerNetworkMod(handler);
        }
        return handler.isNetworkMod();
    }

    public NetworkModHandler findNetworkModHandler(Object mc) {
        if (mc instanceof InjectedModContainer) {
            return networkModHandlers.get(((InjectedModContainer)mc).wrappedContainer);
        }
        if (mc instanceof ModContainer) {
            return networkModHandlers.get(mc);
        } else if (mc instanceof Integer) {
            return networkIdLookup.get(mc);
        } else {
            return networkModHandlers.get(FMLCommonHandler.instance().findContainerFor(mc));
        }
    }

    public Set<ModContainer> getNetworkModList() {
        return networkModHandlers.keySet();
    }

    public static void handlePlayerLogin(EntityPlayerMP player, NetServerHandler netHandler, INetworkManager manager) {
        INetworkRegistry.instance().playerLoggedIn(player, netHandler, manager);
        IGameRegistry.INSTANCE.onLoginLegacy(player);
    }

    public Map<Integer, NetworkModHandler> getNetworkIdMap() {
        return networkIdLookup;
    }

    public void bindNetworkId(String key, Integer value) {
        Map<String, ModContainer> mods = Loader.instance().getIndexedModList();
        NetworkModHandler handler = findNetworkModHandler(mods.get(key));
        if (handler != null) {
            handler.setNetworkId(value);
            networkIdLookup.put(value, handler);
        }
    }

    public static void onClientConnectionToRemoteServer(NetHandler netClientHandler, String server, int port, INetworkManager networkManager) {
        INetworkRegistry.instance().connectionOpened(netClientHandler, server, port, networkManager);
    }

    public static void onClientConnectionToIntegratedServer(NetHandler netClientHandler, MinecraftServer server, INetworkManager networkManager) {
        INetworkRegistry.instance().connectionOpened(netClientHandler, server, networkManager);
    }

    public static void onConnectionClosed(INetworkManager manager, EntityPlayer player) {
        INetworkRegistry.instance().connectionClosed(manager, player);
    }

    public static void openGui(EntityPlayer player, Object mod, int modGuiId, World world, int x, int y, int z) {
        cpw.mods.fml.common.network.internal.FMLNetworkHandler.openGui(player, mod, modGuiId, world, x, y, z);
    }

    public static void makeEntitySpawnAdjustment(int entityId, EntityPlayerMP player, int serverX, int serverY, int serverZ) {
        cpw.mods.fml.common.network.internal.FMLNetworkHandler.makeEntitySpawnAdjustment(
            EntityList.createEntityByID(entityId, player.getEntityWorld()), player, serverX, serverY, serverZ
        );
    }

    public static InetAddress computeLocalHost() throws IOException {
        InetAddress add = null;
        List<InetAddress> addresses = Lists.newArrayList();
        InetAddress localHost = InetAddress.getLocalHost();
        for (NetworkInterface ni : Collections.list(NetworkInterface.getNetworkInterfaces())) {
            if (!ni.isLoopback() && ni.isUp()) {
                addresses.addAll(Collections.list(ni.getInetAddresses()));
                if (addresses.contains(localHost)) {
                    add = localHost;
                    break;
                }
            }
        }
        if (add == null && !addresses.isEmpty()) {
            for (InetAddress ia: addresses) {
                if (ia.getAddress().length == 4) {
                    add = ia;
                    break;
                }
            }
        }
        if (add == null) {
            add = localHost;
        }
        return add;
    }

    public static Packet3Chat handleChatMessage(NetHandler handler, Packet3Chat chat) {
        return INetworkRegistry.instance().handleChat(handler, chat);
    }

    public static void handlePacket131Packet(NetHandler handler, Packet131MapData mapData) {
        if (handler instanceof NetServerHandler || mapData.getItemID() != Item.getIdFromItem(Items.map)) {
            INetworkRegistry.instance().handleTinyPacket(handler, mapData);
        } else {
            IFMLCommonHandler.instance().handleTinyPacket(handler, mapData);
        }
    }

    public static int getCompatibilityLevel() {
        return PROTOCOL_VERSION;
    }

    public static boolean vanillaLoginPacketCompatibility() {
        return true;
    }
}
