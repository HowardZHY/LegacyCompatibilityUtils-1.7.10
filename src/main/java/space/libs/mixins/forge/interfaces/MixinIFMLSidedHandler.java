package space.libs.mixins.forge.interfaces;

import com.google.common.collect.MapDifference;
import cpw.mods.fml.common.IFMLSidedHandler;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.*;
import org.spongepowered.asm.mixin.Mixin;
import space.libs.fml.ItemData;
import space.libs.fml.network.*;
import space.libs.interfaces.IIFMLSidedHandler;

@SuppressWarnings("RedundantMethodOverride")
@Mixin(value = IFMLSidedHandler.class, remap = false)
public interface MixinIFMLSidedHandler extends IIFMLSidedHandler {

    @Override
    default Entity spawnEntityIntoClientWorld(EntityRegistry.EntityRegistration er, EntitySpawnPacket packet) {
        return null;
    }

    @Override
    default void adjustEntityLocationOnClient(EntitySpawnAdjustmentPacket packet) {}

    @Override
    default void sendPacket(Packet packet) {}

    @Override
    default void displayMissingMods(ModMissingPacket modMissingPacket) {}

    @Override
    default void handleTinyPacket(NetHandler handler, Packet131MapData mapData) {}

    @Override
    default void setClientCompatibilityLevel(byte level) {}

    @Override
    default byte getClientCompatibilityLevel() {
        return 0;
    }

    @Override
    default void disconnectIDMismatch(MapDifference<Integer, ItemData> mapDifference, NetHandler netHandler, INetworkManager iNetworkManager) {}

    @Override
    default void updateResourcePackList() {}

}
