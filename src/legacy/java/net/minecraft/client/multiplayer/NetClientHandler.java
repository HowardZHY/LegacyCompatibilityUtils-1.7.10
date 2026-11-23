package net.minecraft.client.multiplayer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.*;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.*;
import org.apache.logging.log4j.LogManager;
import space.libs.fml.network.FMLNetworkHandler;
import space.libs.util.MappedName;

/**
 * @see net.minecraft.client.network.NetHandlerLoginClient
 * @see net.minecraft.client.network.NetHandlerPlayClient
 */
public class NetClientHandler extends NetHandler implements INetClientHandler {

    public static byte connectionCompatibilityLevel;

    public final Minecraft field_72563_h = Minecraft.getMinecraft();

    @Override
    public void func_72455_a(Packet1Login packet1Login) {
        FMLNetworkHandler.onConnectionEstablishedToServer(this, this.getNetworkManager(), packet1Login);
    }

    @Override
    public void func_72494_a(Packet131MapData packet131MapData) {
        FMLNetworkHandler.handlePacket131Packet(this, packet131MapData);
    }

    @Override
    public void func_72468_a(Packet132TileEntityData tileEntityData) {
        int x = tileEntityData.field_73334_a;
        int y = tileEntityData.field_73332_b;
        int z = tileEntityData.field_73333_c;
        if (this.field_72563_h.theWorld.blockExists(x, y, z)) {
            TileEntity tileentity = this.field_72563_h.theWorld.getTileEntity(x, y, z);
            if (tileentity != null) {
                int type = tileEntityData.field_73330_d;
                NBTTagCompound data = tileEntityData.field_73331_e;
                if (type == 1 && tileentity instanceof TileEntityMobSpawner) {
                    tileentity.readFromNBT(data);
                } else if (type == 2 && tileentity instanceof TileEntityCommandBlock) {
                    tileentity.readFromNBT(data);
                } else if (type == 3 && tileentity instanceof TileEntityBeacon) {
                    tileentity.readFromNBT(data);
                } else if (type == 4 && tileentity instanceof TileEntitySkull) {
                    tileentity.readFromNBT(data);
                } else {
                    tileentity.onDataPacket(get(this).netManager, new S35PacketUpdateTileEntity(x, y, z, type, data));
                }
            }
        }
    }

    @Override
    public void func_72501_a(Packet250CustomPayload payload) {
        FMLNetworkHandler.handlePacket250Packet(payload, this.getNetworkManager(), this);
    }

    @Override
    @MappedName("addToSendQueue")
    public void func_74429_a(Packet packet) {
        this.addToSendQueue(packet);
    }

    public void addToSendQueue(net.minecraft.network.Packet packet) {
        get(this).addToSendQueue(packet);
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
        return this.field_72563_h.thePlayer;
    }

    public static void setConnectionCompatibilityLevel(byte connectionCompatibilityLevel) {
        NetClientHandler.connectionCompatibilityLevel = connectionCompatibilityLevel;
    }

    public static byte getConnectionCompatibilityLevel() {
        return connectionCompatibilityLevel;
    }

    public static NetHandlerPlayClient get(INetClientHandler instance) {
        return ((NetHandlerPlayClient) instance);
    }

    public static NetClientHandler getLegacy(INetClientHandler instance) {
        return ((NetClientHandler) instance);
    }
}
