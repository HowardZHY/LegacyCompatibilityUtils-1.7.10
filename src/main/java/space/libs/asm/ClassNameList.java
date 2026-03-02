package space.libs.asm;

import java.util.Arrays;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
public class ClassNameList {

    public static boolean ShouldNotTransform(String name) {
        return (StartsWith(name) || Contains(name));
    }

    public static boolean StartsWith(String name) {
        return Arrays.stream(ClassNameList.WHITELIST).anyMatch(name::startsWith);
    }

    public static boolean StartsWithBlacklist(String name) {
        return Arrays.stream(ClassNameList.BLACKLIST).anyMatch(name::startsWith);
    }

    public static boolean Contains(String name) {
        return Arrays.stream(ClassNameList.CONTAINS_WHITELIST).anyMatch(name::contains);
    }

    /** Things shouldn't be transformed */
    public static String[] WHITELIST;

    /** Things totally shouldn't be transformed */
    public static String[] CONTAINS_WHITELIST = {
        "EarlyMixin",
        "LateMixin",
        "Main",
        "betterfps",
        "makamys",
        "mixinbooter",
        "mixinextra",
        "optifine",
        "shadersmod"
    };

    /** Mod packages that requires additional transforms */
    public static String[] BLACKLIST = {
        ""
    };

    /** Mod that requires additional transforms */
    public static String[] CONTAINS_BLACKLIST = {
        ""
    };

    static {
        WHITELIST = new String[] {
            "barit",
            "club.sk1er.patch",
            "com.goog",
            "com.gtnewh",
            "com.hbm",
            "com.ibm.",
            "com.jcraft",
            "com.llamalad",
            "com.midnight",
            "com.mojang",
            "com.mumfrey.lite",
            "com.replaymod",
            "com.shadowhawk",
            "com.sun",
            "com.thevoxelbox.voxelm",
            "com.typesafe",
            "com.viaver",
            "com.unascribed.ear",
            "com.xuggle",
            "customskin",
            "de.florianmich",
            "dev.tr7",
            "eu.the5z",
            "gg.essen",
            "gnu.trove",
            "groovy",
            "io.github.legacymod",
            "io.netty",
            "it.unimi",
            "java",
            "jopt",
            "junit",
            "net.java.",
            "net.jpointz",
            "net.md_5.spec",
            "net.raphimc",
            "net.weavemc",
            "oshi",
            "org.apache",
            "org.h2",
            "org.intellij",
            "org.jetbrain",
            "org.joml",
            "org.junit",
            "org.lwjgl",
            "org.objectweb",
            "org.scala",
            "org.slf4j",
            "org.spongepower",
            "org.yaml",
            "pauls",
            "scala",
            "sun.",
            "space.lib",
            "tv.twit",
            "watson.cli",
            "wdl",
            "zone.rong",
            "kotlin",

            "cofh",
            "cn.tesser",
            "com.falsepat",
            "com.mitchej",
            "com.prupe",
            "cpw.mods.fml",
            "intermediary",
            "jss",
            "me.flashy",
            "me.jelly",
            "net.coderbot",
            "net.iris",
            "net.fybertech.int",
            "net.minecraft.",
            "net.nevermine",
            "net.tclproject",
            "org.embedded"
        };
    }
}
