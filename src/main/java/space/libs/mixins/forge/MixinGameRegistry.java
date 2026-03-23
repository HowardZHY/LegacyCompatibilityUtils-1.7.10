package space.libs.mixins.forge;

import com.google.common.collect.Lists;

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.IBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.*;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.world.biome.BiomeGenBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import space.libs.fml.*;
import space.libs.interfaces.*;
import space.libs.util.cursedmixinextensions.annotations.Public;
import space.libs.util.forge.RegistryUtils;

import java.lang.reflect.Constructor;
import java.util.List;

@Mixin(value = GameRegistry.class, remap = false)
public class MixinGameRegistry implements IGameRegistry {

    @Public
    private static List<ICraftingHandler> craftingHandlers = Lists.newArrayList();

    @Public
    private static List<IPickupNotifier> pickupHandlers = Lists.newArrayList();

    @Public
    private static List<IPlayerTracker> playerTrackers = Lists.newArrayList();

    @Public
    private static void registerWorldGenerator(IWorldGenerator generator) {
        GameRegistry.registerWorldGenerator(generator, 1);
    }

    @Public
    private static Object buildBlock(ModContainer container, Class<?> type, Block annotation) throws Exception {
        Object o = type.getConstructor(int.class).newInstance(-1);
        registerBlock((net.minecraft.block.Block) o);
        return o;
    }

    @Public
    private static int findSpareBlockId() {
        return BlockTracker.nextBlockId();
    }

    @Public
    private static void registerItem(Item item, String name, String modId) {
        if (!RegistryUtils.LegacyItems.containsKey(item)) {
            GameRegistry.registerItem(item, name, modId);
        }
    }

    @Public
    private static void registerBlock(Block block) {
        registerBlock(block, ItemBlock.class);
    }

    @Public
    private static void registerBlock(Block block, String name) {
        registerBlock(block, ItemBlock.class, name);
    }

    @Public
    private static void registerBlock(Block block, Class<? extends ItemBlock> itemclass) {
        registerBlock(block, itemclass, null);
    }

    @Public
    private static void registerBlock(Block block, Class<? extends ItemBlock> itemclass, String name) {
        registerBlock(block, itemclass, name, null);
    }

    @SuppressWarnings("deprecation")
    @Public
    private static void registerBlock(Block block, Class<? extends ItemBlock> itemclass, String name, String modId) {
        GameRegistry.registerBlock(block, itemclass, name, modId);
    }

    @Inject(
        method = "registerBlock(Lnet/minecraft/block/Block;Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Object;)Lnet/minecraft/block/Block;",
        at = @At(
            value = "INVOKE",
            target = "Ljava/lang/Class;getConstructor([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;"
        ),
        cancellable = true
    )
    private static void registerBlock(Block block, Class<? extends ItemBlock> itemclass, String name, Object[] itemCtorArgs, CallbackInfoReturnable<Block> cir) {
        IBlock accessor = (IBlock) block;
        if (accessor.IsLegacyBlock()) {
            if (itemCtorArgs == null || itemCtorArgs.length == 0) {
                String unlocalizedName = accessor.RawUnlocalizedName();
                if (name == null || name.isEmpty()) {
                    name = unlocalizedName;
                } else {
                    if (!name.equals(unlocalizedName)) {
                        IRegistryNamespaced.renameBlock(name, block);
                    }
                }
                int blockItemId;
                if (accessor.IsLegacyBlockNoID()) {
                    blockItemId = RegistryUtils.registerBlockAutoID(block, name) - 256;
                } else {
                    blockItemId = accessor.GetLegacyID() - 256;
                }
                ItemBlock item = null;
                try {
                    Constructor<? extends ItemBlock> itemCtor = itemclass.getConstructor(int.class);
                    item = itemCtor.newInstance(blockItemId);
                } catch (ReflectiveOperationException ignored) {
                    try {
                        Constructor<? extends ItemBlock> itemCtor = itemclass.getConstructor(int.class, Block.class);
                        item = itemCtor.newInstance(blockItemId, block);
                    } catch (ReflectiveOperationException ignored1) {}
                }
                if (item != null) {
                    item.setUnlocalizedName(name);
                }
                cir.setReturnValue(block);
            }
        }
    }

    @Public
    private static void addSmelting(int input, ItemStack output, float xp) {
        FurnaceRecipes.instance().addSmelting(Item.getItemById(input), output, xp);
    }

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

    @Public
    private static void registerPlayerTracker(IPlayerTracker tracker) {
        playerTrackers.add(tracker);
    }

    @Public
    private static void onPlayerLogin(EntityPlayer player) {
        for (IPlayerTracker tracker : playerTrackers) {
            try {
                tracker.onPlayerLogin(player);
            } catch (Exception e) {
                FMLLog.severe( "A critical error occurred handling the onPlayerLogin event with player tracker %s", tracker.getClass().getName());
                e.printStackTrace();
            }
        }
    }

    @Public
    private static void onPlayerLogout(EntityPlayer player) {
        for (IPlayerTracker tracker : playerTrackers) {
            try {
                tracker.onPlayerLogout(player);
            } catch (Exception e) {
                FMLLog.severe("A critical error occurred handling the onPlayerLogout event with player tracker %s", tracker.getClass().getName());
                e.printStackTrace();
            }
        }
    }

    @Public
    private static void onPlayerChangedDimension(EntityPlayer player) {
        for (IPlayerTracker tracker : playerTrackers) {
            try {
                tracker.onPlayerChangedDimension(player);
            } catch (Exception e) {
                FMLLog.severe("A critical error occurred handling the onPlayerChangedDimension event with player tracker %s", tracker.getClass().getName());
                e.printStackTrace();
            }
        }
    }

    @Public
    private static void onPlayerRespawn(EntityPlayer player) {
        for (IPlayerTracker tracker : playerTrackers) {
            try {
                tracker.onPlayerRespawn(player);
            } catch (Exception e) {
                FMLLog.severe("A critical error occurred handling the onPlayerRespawn event with player tracker %s", tracker.getClass().getName());
                e.printStackTrace();
            }
        }
    }

    @Override
    public void addBiomeLegacy(BiomeGenBase biome) {
        addBiome(biome);
    }

    @Override
    public void removeBiomeLegacy(BiomeGenBase biome) {
        removeBiome(biome);
    }

    @Override
    public void registerCraftingHandle(ICraftingHandler handler) {
        registerCraftingHandler(handler);
    }

    @Override
    public void registerPickupHandle(IPickupNotifier handler) {
        registerPickupHandler(handler);
    }

    @Override
    public void onItemCraft(EntityPlayer player, ItemStack item, IInventory craftMatrix) {
        onItemCrafted(player, item, craftMatrix);
    }

    @Override
    public void onItemSmelt(EntityPlayer player, ItemStack item) {
        onItemSmelted(player, item);
    }

    @Override
    public void onPickupNotify(EntityPlayer player, EntityItem item) {
        onPickupNotification(player, item);
    }

    @Override
    public void onLoginLegacy(EntityPlayer player) {
        onPlayerLogin(player);
    }

    @Override
    public void onLogoutLegacy(EntityPlayer player) {
        onPlayerLogout(player);
    }

    @Override
    public void onChangedDimensionLegacy(EntityPlayer player) {
        onPlayerChangedDimension(player);
    }

    @Override
    public void onRespawnLegacy(EntityPlayer player) {
        onPlayerRespawn(player);
    }

    @Override
    public List<ICraftingHandler> getCraftingHandlers() {
        return craftingHandlers;
    }

    @Override
    public List<IPickupNotifier> getPickupHandlers() {
        return pickupHandlers;
    }

    @Override
    public List<IPlayerTracker> getPlayerTrackers() {
        return playerTrackers;
    }

}
