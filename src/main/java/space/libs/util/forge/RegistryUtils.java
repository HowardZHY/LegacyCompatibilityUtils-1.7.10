package space.libs.util.forge;

import com.google.common.collect.Maps;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import space.libs.CompatLib;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

@SuppressWarnings("JavaReflectionMemberAccess")
public class RegistryUtils {

    public static GameData GameData;

    public static Method RegisterBlock;

    public static Map<Block, Integer> LegacyBlocks = Maps.newHashMap();

    public static Map<Item, Integer> LegacyItems = Maps.newHashMap();

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
            CompatLib.LOGGER.info("Registering Legacy Block " + block.getClass() + " " + name + " " + id);
            Block.blockRegistry.addObject(id, name, block);
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
            CompatLib.LOGGER.info("Registering Legacy Item " + item.getClass() + " " + name + " " + id);
            Item.itemRegistry.addObject(id, name, item);
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
            return (int) RegisterBlock.invoke(GameData, block, name);
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
            GameData = (GameData) main.invoke(null);
            RegisterBlock = GameData.class.getDeclaredMethod("registerBlock", Block.class, String.class);
            RegisterBlock.setAccessible(true);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
