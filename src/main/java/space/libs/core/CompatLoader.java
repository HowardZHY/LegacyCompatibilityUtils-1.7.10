package space.libs.core;

import cpw.mods.fml.common.launcher.FMLTweaker;
import cpw.mods.fml.relauncher.CoreModManager;
import cpw.mods.fml.relauncher.ModListHelper;
import space.libs.util.PathUtils;

import java.io.File;
import java.lang.reflect.Field;

@SuppressWarnings("unused")
public class CompatLoader {

    public CompatLoader() {
        super();
    }

    public static FMLTweaker tweaker;

    public static void init() {
        try {
            Field f = CoreModManager.class.getDeclaredField("tweaker");
            f.setAccessible(true);
            tweaker = (FMLTweaker) f.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        File customModDir = new File(PathUtils.MOD_DIRECTORY.toFile(), "compatlib");
        if (customModDir.exists() && customModDir.isDirectory()) {
            addModsFromDirectory(customModDir);
        }
    }

    private static void addModsFromDirectory(File directory) {
        File[] modFiles = directory.listFiles();
        if (modFiles != null) {
            for (File modFile : modFiles) {
                if (modFile.isFile() && (modFile.getName().endsWith(".jar") || modFile.getName().endsWith(".zip"))) {
                    ModListHelper.additionalMods.put(modFile.getName(), modFile);
                }
            }
        }
    }

}
