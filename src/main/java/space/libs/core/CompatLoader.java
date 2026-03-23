package space.libs.core;

import cpw.mods.fml.common.launcher.FMLTweaker;
import cpw.mods.fml.relauncher.*;
import net.minecraft.launchwrapper.Launch;
import space.libs.util.PathUtils;

import java.io.File;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.file.*;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class CompatLoader {

    @SuppressWarnings("unused")
    public static FMLTweaker tweaker = ReflectionHelper.getPrivateValue(CoreModManager.class, null, "tweaker");

    public static final Version[] VERSIONS = new Version[127];

    public static boolean INIT = false;

    public static File CompatLibFolder;

    public static void init() {
        if (!INIT) {
            INIT = true;
            CompatLibFolder = new File(PathUtils.MOD_DIRECTORY.toFile(), "compatlib");
            try {
                Files.createDirectories(CompatLibFolder.toPath());
                new Version(62);
                new Version(64);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static boolean addLegacyMods(int ver) {
        File directory = getLegacyModsFolder(ver);
        Version version = VERSIONS[ver];
        if (directory.exists() && directory.isDirectory()) {
            File[] modFiles = directory.listFiles();
            if (modFiles != null) {
                for (File modFile : modFiles) {
                    if (modFile.isFile() && (modFile.getName().endsWith(".jar") || modFile.getName().endsWith(".zip"))) {
                        ModListHelper.additionalMods.put(modFile.getName(), modFile);
                        Path p = modFile.toPath().toAbsolutePath().normalize();
                        version.Mods.add(p);
                    }
                }
            }
        }
        return version.Mods.isEmpty();
    }

    public static File getLegacyModsFolder(int ver) {
        return new File(CompatLibFolder, "1." + ver / 10 + "." + ver % 10);
    }

    public static boolean notAvailable(int ver) {
        if (!INIT) {
            return true;
        } else {
            Version version = VERSIONS[ver];
            return (version == null || version.noMods);
        }
    }

    public static boolean isFromLegacyJar(String className, int ver) {
        if (notAvailable(ver)) {
            return false;
        } else {
            String res = className.replace('.', '/').concat(".class");
            try {
                URL url = Launch.classLoader.getResource(res);
                if (url == null || !"jar".equals(url.getProtocol())) {
                    return false;
                }
                JarURLConnection conn = (JarURLConnection) url.openConnection();
                URL jarFileUrl = conn.getJarFileURL();
                Path jarPath = Paths.get(jarFileUrl.toURI()).toAbsolutePath().normalize();
                return VERSIONS[ver].Mods.contains(jarPath);
            } catch (Exception e) {
                CompatLibCore.LOGGER.warn(e);
                return false;
            }
        }
    }

    public static class Version {

        public final Set<Path> Mods;

        public final boolean noMods;

        public Version(final int ver) throws Exception {
            this.Mods = ConcurrentHashMap.newKeySet();
            VERSIONS[ver] = this;
            Files.createDirectories(getLegacyModsFolder(ver).toPath());
            noMods = addLegacyMods(ver);
        }
    }
}
