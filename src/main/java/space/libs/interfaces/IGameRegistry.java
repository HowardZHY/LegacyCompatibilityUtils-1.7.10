package space.libs.interfaces;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import space.libs.fml.ICraftingHandler;
import space.libs.fml.IPickupNotifier;
import space.libs.fml.IPlayerTracker;

import java.util.List;

@SuppressWarnings("unused")
public interface IGameRegistry {

    @SuppressWarnings("all")
    IGameRegistry INSTANCE = (IGameRegistry) new GameRegistry();

    List<ICraftingHandler> getCraftingHandlers();

    List<IPickupNotifier> getPickupHandlers();

    List<IPlayerTracker> getPlayerTrackers();

    void addBiomeLegacy(BiomeGenBase biome);

    void removeBiomeLegacy(BiomeGenBase biome);

    void registerCraftingHandle(ICraftingHandler handler);

    void registerPickupHandle(IPickupNotifier handler);

    void onItemCraft(EntityPlayer player, ItemStack item, IInventory craftMatrix);

    void onItemSmelt(EntityPlayer player, ItemStack item);

    void onPickupNotify(EntityPlayer player, EntityItem item);

}
