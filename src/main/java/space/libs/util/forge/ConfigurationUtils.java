package space.libs.util.forge;

import cpw.mods.fml.common.FMLLog;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.util.Arrays;

@SuppressWarnings("unused")
public class ConfigurationUtils {

    public static boolean[] configMarkers = new boolean[32000];

    public static final int ITEM_SHIFT = 256;

    public static final int MAX_BLOCKS = 4096;

    public static Property getBlock(Configuration instance, String category, String key, int defaultID, String comment, int lower, int upper) {
        Property prop = instance.get(category, key, -1, comment);
        if (prop.getInt() != -1) {
            configMarkers[prop.getInt()] = true;
            return prop;
        } else {
            if (defaultID < lower) {
                FMLLog.warning(
                    "Mod attempted to get a block ID with a default in the Terrain Generation section, " +
                        "mod authors should make sure there defaults are above 256 unless explicitly needed " +
                        "for terrain generation. Most ores do not need to be below 256.");
                FMLLog.warning("Config \"%s\" Category: \"%s\" Key: \"%s\" Default: %d", instance.getConfigFile().getAbsolutePath(), category, key, defaultID);
                defaultID = upper - 1;
            }
            if (RegistryUtils.getBlocksList()[defaultID] == null && !configMarkers[defaultID]) {
                prop.set(defaultID);
                configMarkers[defaultID] = true;
                return prop;
            } else {
                for (int j = upper - 1; j > 0; j--) {
                    if (RegistryUtils.getBlocksList()[j] == null && !configMarkers[j]) {
                        prop.set(j);
                        configMarkers[j] = true;
                        return prop;
                    }
                }
                throw new RuntimeException("No more block ids available for " + key);
            }
        }
    }

    public static Property getItem(Configuration instance, String category, String key, int defaultID, String comment) {
        Property prop = instance.get(category, key, -1, comment);
        int defaultShift = defaultID + ITEM_SHIFT;
        if (prop.getInt() != -1) {
            configMarkers[prop.getInt() + ITEM_SHIFT] = true;
            return prop;
        } else {
            if (defaultID < MAX_BLOCKS - ITEM_SHIFT) {
                FMLLog.warning(
                    "Mod attempted to get a item ID with a default value in the block ID section, " +
                        "mod authors should make sure there defaults are above %d unless explicitly needed " +
                        "so that all block ids are free to store blocks.", MAX_BLOCKS - ITEM_SHIFT);
                FMLLog.warning("Config \"%s\" Category: \"%s\" Key: \"%s\" Default: %d", instance.getConfigFile().getAbsolutePath(), category, key, defaultID);
            }
            if (RegistryUtils.getItemsList()[defaultShift] == null && !configMarkers[defaultShift] && defaultShift >= RegistryUtils.getBlocksList().length) {
                prop.set(defaultID);
                configMarkers[defaultShift] = true;
                return prop;
            } else {
                for (int x = RegistryUtils.getItemsList().length - 1; x >= ITEM_SHIFT; x--) {
                    if (RegistryUtils.getItemsList()[x] == null && !configMarkers[x]) {
                        prop.set(x - ITEM_SHIFT);
                        configMarkers[x] = true;
                        return prop;
                    }
                }
                throw new RuntimeException("No more item ids available for " + key);
            }
        }
    }

    static {
        Arrays.fill(configMarkers, false);
    }
}
