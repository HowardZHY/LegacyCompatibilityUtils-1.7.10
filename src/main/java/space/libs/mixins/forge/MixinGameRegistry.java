package space.libs.mixins.forge;

import com.google.common.collect.Lists;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import org.spongepowered.asm.mixin.Mixin;
import space.libs.fml.*;
import space.libs.interfaces.IGameRegistry;
import space.libs.interfaces.IWorldType;
import space.libs.util.cursedmixinextensions.annotations.Public;

import java.util.List;

@SuppressWarnings("all")
@Mixin(value = GameRegistry.class, remap = false)
public class MixinGameRegistry implements IGameRegistry {

    @Public
    private static List<ICraftingHandler> craftingHandlers = Lists.newArrayList();

    @Public
    private static List<IPickupNotifier> pickupHandlers = Lists.newArrayList();

    @Public
    private static List<IPlayerTracker> playerTrackers = Lists.newArrayList();

    @Public
    private static void addBiome(BiomeGenBase biome) {
        IWorldType.DEFAULT.addNewBiome(biome);
    }

    @Public
    private static void removeBiome(BiomeGenBase biome) {
        IWorldType.DEFAULT.removeBiome(biome);
    }

    @Public
    private static void registerCraftingHandler(ICraftingHandler handler) {
        craftingHandlers.add(handler);
    }

    @Public
    private static void onItemCrafted(EntityPlayer player, ItemStack item, IInventory craftMatrix) {
        for (ICraftingHandler handler : craftingHandlers) {
            handler.onCrafting(player, item, craftMatrix);
        }
    }

    @Public
    private static void onItemSmelted(EntityPlayer player, ItemStack item) {
        for (ICraftingHandler handler : craftingHandlers) {
            handler.onSmelting(player, item);
        }
    }

    @Public
    private static void registerPickupHandler(IPickupNotifier handler) {
        pickupHandlers.add(handler);
    }

    @Public
    private static void onPickupNotification(EntityPlayer player, EntityItem item) {
        for (IPickupNotifier notify : pickupHandlers) {
            notify.notifyPickup(item, player);
        }
    }

    public void addBiomeLegacy(BiomeGenBase biome) {
        addBiome(biome);
    }

    public void removeBiomeLegacy(BiomeGenBase biome) {
        removeBiome(biome);
    }

    public void registerCraftingHandle(ICraftingHandler handler) {
        registerCraftingHandler(handler);
    }

    public void registerPickupHandle(IPickupNotifier handler) {
        registerPickupHandler(handler);
    }

    public void onItemCraft(EntityPlayer player, ItemStack item, IInventory craftMatrix) {
        onItemCrafted(player, item, craftMatrix);
    }

    public void onItemSmelt(EntityPlayer player, ItemStack item) {
        onItemSmelted(player, item);
    }

    public void onPickupNotify(EntityPlayer player, EntityItem item) {
        onPickupNotification(player, item);
    }

    public List<ICraftingHandler> getCraftingHandlers() {
        return craftingHandlers;
    }

    public List<IPickupNotifier> getPickupHandlers() {
        return pickupHandlers;
    }

    public List<IPlayerTracker> getPlayerTrackers() {
        return playerTrackers;
    }

}
