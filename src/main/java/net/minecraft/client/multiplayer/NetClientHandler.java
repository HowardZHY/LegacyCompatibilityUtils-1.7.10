package net.minecraft.client.multiplayer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.*;
import org.apache.logging.log4j.LogManager;
import space.libs.fml.network.FMLNetworkHandler;

/**
 * @see net.minecraft.client.network.NetHandlerLoginClient
 * @see net.minecraft.client.network.NetHandlerPlayClient
 */
public class NetClientHandler extends NetHandler {

    public static byte connectionCompatibilityLevel;

    public final Minecraft field_72563_h = Minecraft.getMinecraft();

    @Override
    public void func_72455_a(Packet1Login packet1Login) {
        FMLNetworkHandler.onConnectionEstablishedToServer(this, this.getNetworkManager(), packet1Login);
    }

    @Override
    public void func_72501_a(Packet250CustomPayload payload) {
        FMLNetworkHandler.handlePacket250Packet(payload, this.getNetworkManager(), this);
    }

    public void fmlPacket131Callback(Packet131MapData data) {
        if (data.field_73438_a == Item.getIdFromItem(Items.map)) {
            ItemMap.loadMapData(data.field_73436_b, this.field_72563_h.theWorld).updateMPMapData(data.field_73437_c);
        } else {
            LogManager.getLogger().warn("Unknown itemId: " + data.field_73436_b);
        }
    }

    @Override
    public EntityPlayer getPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }

    public static void setConnectionCompatibilityLevel(byte connectionCompatibilityLevel) {
        NetClientHandler.connectionCompatibilityLevel = connectionCompatibilityLevel;
    }

    public static byte getConnectionCompatibilityLevel() {
        return connectionCompatibilityLevel;
    }

    public INetworkManager getNetworkManager() {
        return (INetworkManager) get(this).netManager;
    }

    @SuppressWarnings("DataFlowIssue")
    public static NetHandlerPlayClient get(NetClientHandler instance) {
        return ((NetHandlerPlayClient) (Object) instance);
    }
}
