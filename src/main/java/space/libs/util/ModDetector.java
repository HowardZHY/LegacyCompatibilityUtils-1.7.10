package space.libs.util;

import net.minecraft.launchwrapper.Launch;

import java.io.IOException;

public class ModDetector {

    public static boolean AOA1 = false;

    public static boolean AOA2 = false;

    public static boolean hasClassBytes(String name) {
        byte[] bytes = null;
        try {
            bytes = Launch.classLoader.getClassBytes(name);
        } catch (IOException ignored) {}
        return bytes != null;
    }
}
