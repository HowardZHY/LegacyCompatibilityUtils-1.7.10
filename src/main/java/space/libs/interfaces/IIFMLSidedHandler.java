package space.libs.interfaces;

import com.google.common.collect.MapDifference;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.*;
import space.libs.fml.ItemData;
import space.libs.fml.network.*;

public interface IIFMLSidedHandler {

    Entity spawnEntityIntoClientWorld(EntityRegistry.EntityRegistration er, EntitySpawnPacket packet);

    void adjustEntityLocationOnClient(EntitySpawnAdjustmentPacket packet);

    void sendPacket(Packet packet);

    void displayMissingMods(ModMissingPacket modMissingPacket);

    void handleTinyPacket(NetHandler handler, Packet131MapData mapData);

    void setClientCompatibilityLevel(byte level);

    byte getClientCompatibilityLevel();

    void disconnectIDMismatch(MapDifference<Integer, ItemData> mapDifference, NetHandler netHandler, INetworkManager iNetworkManager);

    void updateResourcePackList();

}
