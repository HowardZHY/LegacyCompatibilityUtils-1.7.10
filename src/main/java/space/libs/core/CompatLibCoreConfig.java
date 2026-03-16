package space.libs.core;

@SuppressWarnings("unused")
public class CompatLibCoreConfig {

    public static boolean BaseMod;

    public static boolean LegacyDeobf;

    public static void init() {
        BaseMod = Boolean.parseBoolean(System.getProperty("compatlib.BaseMod"));
        LegacyDeobf = Boolean.parseBoolean(System.getProperty("compatlib.LegacyDeobf"));
    }
}
