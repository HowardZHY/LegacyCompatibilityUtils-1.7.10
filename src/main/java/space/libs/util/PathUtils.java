package space.libs.util;

import net.minecraft.launchwrapper.Launch;

import java.nio.file.Path;

@SuppressWarnings("unused")
public abstract class PathUtils {

    public static final Path CONFIG_DIR = Launch.minecraftHome.toPath().resolve("config");

    public static final Path MOD_DIRECTORY = Launch.minecraftHome.toPath().resolve("mods");

}
