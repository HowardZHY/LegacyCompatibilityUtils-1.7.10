package space.libs.util;

import net.minecraft.launchwrapper.Launch;

import java.nio.file.*;

@SuppressWarnings("unused")
public interface IPathUtils {

    Path CONFIG_DIR = Launch.minecraftHome.toPath().resolve("config");

    Path MOD_DIRECTORY = Launch.minecraftHome.toPath().resolve("mods");

    static void createDir(Path path) {
        try {
            Files.createDirectories(path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
