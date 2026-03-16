package space.libs.core;

import cpw.mods.fml.common.launcher.FMLTweaker;
import cpw.mods.fml.relauncher.*;
import net.minecraft.launchwrapper.Launch;
import space.libs.util.PathUtils;

import java.io.File;
import java.net.URL;
import java.nio.file.*;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class CompatLoader {

    @SuppressWarnings("unused")
    public static FMLTweaker tweaker = ReflectionHelper.getPrivateValue(CoreModManager.class, null, "tweaker");

    public static boolean INIT = false;

    public static final Set<Path> MC164 = ConcurrentHashMap.newKeySet();

    public static final Set<Path> MC162 = ConcurrentHashMap.newKeySet();

    public static final Set<Path> MC161 = ConcurrentHashMap.newKeySet();

    public static File CompatLibFolder;

    public static void init() {
        if (!INIT) {
            INIT = true;
            CompatLibFolder = new File(PathUtils.MOD_DIRECTORY.toFile(), "compatlib");
            try {
                Files.createDirectories(CompatLibFolder.toPath());
                Files.createDirectories(getLegacyModsFolder(62).toPath());
                Files.createDirectories(getLegacyModsFolder(64).toPath());
            } catch (Exception ignored) {}
            addLegacyMods(62);
            addLegacyMods(64);
        }
    }

    public static void addLegacyMods(int ver) {
        File directory = getLegacyModsFolder(ver);
        if (directory.exists() && directory.isDirectory()) {
            File[] modFiles = directory.listFiles();
            if (modFiles != null) {
                for (File modFile : modFiles) {
                    if (modFile.isFile() && (modFile.getName().endsWith(".jar") || modFile.getName().endsWith(".zip"))) {
                        ModListHelper.additionalMods.put(modFile.getName(), modFile);
                        Path p = modFile.toPath().toAbsolutePath().normalize();
                        switch (ver) {
                            case 61:
                                MC161.add(p);
                            case 62:
                                MC162.add(p);
                                break;
                            case 64:
                                MC164.add(p);
                                break;
                            default: {}
                        }
                    }
                }
            }
        }
    }

    public static File getLegacyModsFolder(int ver) {
        return new File(CompatLibFolder, "1." + ver / 10 + "." + ver % 10);
    }

    public static boolean isFromLegacyJar(String className, int ver) {
        if (!INIT) {
            return false;
        } else {
            String res = className.replace('.', '/') + ".class";
            try {
                URL url = Launch.classLoader.getResource(res);
                if (url == null) {
                    return false;
                }
                String s = url.toString();
                int ang = s.indexOf("!/");
                if (!s.startsWith("jar:") || ang < 0) {
                    return false;
                }
                String jarPart = s.substring(4, ang);
                Path jarPath = Paths.get(new URL(jarPart).toURI()).toAbsolutePath().normalize();
                switch (ver) {
                    case 62:
                        return MC162.contains(jarPath);
                    case 64:
                        return MC164.contains(jarPath);
                    default:
                        return false;
                }
            } catch (Exception e) {
                CompatLibCore.LOGGER.warn(e);
                return false;
            }
        }
    }
}
