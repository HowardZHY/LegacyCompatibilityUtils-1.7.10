package space.libs.util.forge;

import com.google.common.collect.*;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.RegistryNamespaced;
import space.libs.CompatLib;

import java.lang.reflect.*;
import java.util.*;

@SuppressWarnings("JavaReflectionMemberAccess")
public abstract class RegistryUtils {

    public static GameData Data;

    public static Method RegisterBlock;

    public static Map<Block, Integer> LegacyBlocks = Maps.newHashMap();

    public static Map<Item, Integer> LegacyItems = Maps.newHashMap();

    public static Set<Class<? extends Block>> RegisteredBlocks = Sets.newHashSet();

    public static Set<Class<? extends Item>> RegisteredItems = Sets.newHashSet();

    public static Field BlocksLists;

    public static Field ItemsLists;

    public static void putBlock(Block block, int id) {
        LegacyBlocks.put(block, id);
    }

    public static void putItem(Item item, int id) {
        LegacyItems.put(item, id);
    }

    public static void registerLegacyBlock(Block block, String name)  {
        if (Block.getIdFromBlock(block) > 0) {
            return;
        }
        try {
            int id = LegacyBlocks.get(block);
            RegistryNamespaced registry = Block.blockRegistry;
            Class<? extends Block> blockClass = block.getClass();
            if (RegisteredBlocks.contains(blockClass) && registry.getObject(name) != null) {
                name = name + "_" + id;
            }
            RegisteredBlocks.add(blockClass);
            CompatLib.LOGGER.info("Registering Legacy Block " + blockClass + " " + name + " " + id);
            registry.addObject(id, name, block);
        } catch (Exception e) {
            CompatLib.LOGGER.error("Couldn't Register Legacy Block!", e);
            e.printStackTrace();
        }
    }

    public static void registerLegacyItem(Item item, String name) {
        if (Item.getIdFromItem(item) > 0) {
            return;
        }
        try {
            int id = LegacyItems.get(item);
            RegistryNamespaced registry = Item.itemRegistry;
            Class<? extends Item> itemClass = item.getClass();
            if (RegisteredItems.contains(itemClass) && registry.getObject(name) != null) {
                name = name + "_" + id;
            }
            RegisteredItems.add(itemClass);
            CompatLib.LOGGER.info("Registering Legacy Item " + itemClass + " " + name + " " + id);
            registry.addObject(id, name, item);
        } catch (Exception e) {
            CompatLib.LOGGER.error("Couldn't Register Legacy Item!", e);
            e.printStackTrace();
        }
    }

    public static Block[] getBlocksList() {
        Block[] blocks;
        try {
            blocks = (Block[]) BlocksLists.get(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return blocks;
    }

    public static Item[] getItemsList() {
        Item[] items;
        try {
            items = (Item[]) ItemsLists.get(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return items;
    }

    public static int registerBlockAutoID(Block block, String name) {
        try {
            return (int) RegisterBlock.invoke(Data, block, name);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean is17Block(int id) {
        if (id > 159) {
            return (id < 165 || id == 174 || id == 175);
        }
        return false;
    }

    static {
        try {
            BlocksLists = Block.class.getDeclaredField("field_71973_m");
            ItemsLists = Item.class.getDeclaredField("field_77698_e");
            Method main = GameData.class.getDeclaredMethod("getMain");
            main.setAccessible(true);
            Data = (GameData) main.invoke(null);
            RegisterBlock = GameData.class.getDeclaredMethod("registerBlock", Block.class, String.class);
            RegisterBlock.setAccessible(true);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
