package space.libs.util.forge;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import space.libs.CompatLib;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Map;

@SuppressWarnings("JavaReflectionMemberAccess")
public class RegistryUtils {

    public static Map<Block, Integer> LegacyBlocks = Maps.newHashMap();

    public static Map<Item, Integer> LegacyItems = Maps.newHashMap();

    public static Field BlocksLists;

    public static Field ItemsLists;

    public static Constructor<?> ItemBlock;

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
            registerItemBlock(block, id);
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

    public static void registerItemBlock(Block block, int id) {
        try {
            ((ItemBlock) ItemBlock.newInstance(id)).setUnlocalizedName(block.getUnlocalizedName());
        } catch (Exception e) {
            CompatLib.LOGGER.error("Couldn't Construct Legacy ItemBlock!", e);
            throw new RuntimeException(e);
        }
    }

    static {
        try {
            BlocksLists = Block.class.getDeclaredField("field_71973_m");
            ItemsLists = Item.class.getDeclaredField("field_77698_e");
            ItemBlock = ItemBlock.class.getDeclaredConstructor(int.class);
        } catch (NoSuchFieldException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
