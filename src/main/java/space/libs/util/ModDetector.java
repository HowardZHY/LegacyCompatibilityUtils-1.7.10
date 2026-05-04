package space.libs.util;

import space.libs.core.*;

import java.io.*;

public class ModDetector implements ICoreUtils {

    public static boolean AOA1 = false;

    public static boolean AOA2 = false;

    public static boolean hasClassBytes(String name) {
        byte[] bytes = null;
        try {
            bytes = classLoader.getClassBytes(name);
        } catch (IOException ignored) {}
        return bytes != null;
    }
}
