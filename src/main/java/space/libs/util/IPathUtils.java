package space.libs.util;

import net.minecraft.launchwrapper.Launch;

import java.io.File;
import java.nio.file.*;

@SuppressWarnings("unused")
public interface IPathUtils {

    Path CONFIG_DIR = resolve("config");

    Path MOD_DIRECTORY = resolve("mods");

    static void createDir(Path path) {
        try {
            Files.createDirectories(path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static Path resolve(String folder) {
        File base = Launch.minecraftHome;
        if (base == null) {
            return Paths.get(".").resolve(folder);
        } else {
            return base.toPath().resolve(folder);
        }
    }
}
