package space.libs.mixins.forge;

import com.google.common.collect.MapDifference;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.server.FMLServerHandler;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet131MapData;
import org.spongepowered.asm.mixin.Mixin;
import space.libs.fml.ItemData;
import space.libs.fml.network.EntitySpawnAdjustmentPacket;
import space.libs.fml.network.EntitySpawnPacket;
import space.libs.fml.network.ModMissingPacket;
import space.libs.interfaces.IIFMLSidedHandler;

@Mixin(value = FMLServerHandler.class, remap = false)
public class MixinFMLServerHandler implements IIFMLSidedHandler {

    /**
     * @implNote NO-OP
     */
    @Override
    public Entity spawnEntityIntoClientWorld(EntityRegistry.EntityRegistration er, EntitySpawnPacket packet) {
        return null;
    }

    @Override
    public void adjustEntityLocationOnClient(EntitySpawnAdjustmentPacket packet) {}

    @Override
    public void sendPacket(Packet packet) {
        throw new RuntimeException("You cannot send a bare packet without a target on the server!");
    }

    @Override
    public void displayMissingMods(ModMissingPacket modMissingPacket) {}

    @Override
    public void handleTinyPacket(NetHandler handler, Packet131MapData mapData) {}

    @Override
    public void setClientCompatibilityLevel(byte level) {}

    @Override
    public byte getClientCompatibilityLevel() {
        return 0;
    }

    @Override
    public void disconnectIDMismatch(MapDifference<Integer, ItemData> mapDifference, NetHandler netHandler, INetworkManager iNetworkManager) {}

    @Override
    public void updateResourcePackList() {}

}
