package space.libs.asm;

import java.util.Arrays;

@SuppressWarnings("unused")
public class ClassNameList {

    public static boolean Startswith(String name) {
        return Arrays.stream(ClassNameList.WHITELIST).anyMatch(name::startsWith);
    }

    public static boolean StartswithBlacklist(String name) {
        return Arrays.stream(ClassNameList.BLACKLIST).anyMatch(name::startsWith);
    }

    public static boolean Contains(String name) {
        return Arrays.stream(ClassNameList.CONTAINS_WHITELIST).anyMatch(name::contains);
    }

    /** Things shouldn't be transformed */
    public static String[] WHITELIST = {
        "barit",
        "club.sk1er.patch",
        "cofh",
        "cn.tesser",
        "com.falsepat",
        "com.hbm",
        "com.jcraft",
        "com.llamalad",
        "com.mitchej",
        "com.mojang",
        "com.mumfrey.lite",
        "com.goog",
        "com.gtnewh",
        "com.replaymod",
        "com.shadowhawk",
        "com.prupe",
        "com.sun",
        "com.thevoxelbox.voxelm",
        "com.viaver",
        "com.unascribed.ear",
        "com.xuggle",
        "cpw.mods.fml",
        "customskin",
        "de.florianmich",
        "dev.tr7zw",
        "eu.the5zig",
        "gg.essen",
        "gnu.trove",
        "groovy",
        "intermediary",
        "io.github.legacymod",
        "io.netty",
        "it.unimi",
        "java",
        "jopt",
        "jss",
        "junit",
        "me.flashy",
        "me.jelly",
        "net.coderbot",
        "net.iris",
        "net.jpointz",
        "net.md_5.spec",
        "net.fybertech.int",
        "net.minecraft.",
        "net.nevermine",
        "net.raphimc",
        "net.tclproject",
        "net.weavemc",
        "oshi",
        "org.apache",
        "org.embedded",
        "org.h2",
        "org.intellij",
        "org.jetbrain",
        "org.joml",
        "org.junit",
        "org.lwjgl",
        "org.objectweb",
        "org.slf4j",
        "org.spongepower",
        "org.yaml",
        "pauls",
        "scala",
        "space.lib",
        "sun.",
        "tv.twit",
        "watson.cli",
        "wdl",
        "zone.rong",
        "kotlin"
    };

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

}
