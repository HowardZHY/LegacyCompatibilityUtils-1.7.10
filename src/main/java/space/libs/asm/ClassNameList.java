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
        "baritone",
        "club.sk1er.patcher",
        "cofh",
        "com.jcraft",
        "com.llamalad7",
        "com.mojang",
        "com.mumfrey.liteloader",
        "com.google",
        "com.gtnewhorizon",
        "com.replaymod",
        "com.shadowhawk",
        "com.prupe",
        "com.sun",
        "com.thevoxelbox.voxelmap",
        "com.viaversion",
        "com.unascribed.ears",
        "com.xuggle",
        "cpw.mods.fml",
        "customskinloader",
        "de.florianmichael",
        "dev.tr7zw",
        "eu.the5zig",
        "gg.essential",
        "gnu.trove",
        "groovy",
        "intermediary",
        "io.github.legacymoddingmc",
        "io.netty",
        "it.unimi",
        "java",
        "javax",
        "javaassist",
        "joptsimple",
        "junit",
        "net.jpointz",
        "net.md_5.specialsource",
        "net.fybertech.intermediary",
        "net.minecraft.",
        "net.raphimc",
        "net.weavemc",
        "oshi",
        "org.apache",
        "org.h2",
        "org.intellij",
        "org.jetbrains",
        "org.joml",
        "org.junit",
        "org.lwjgl",
        "org.objectweb",
        "org.slf4j",
        "org.spongepowered",
        "org.yaml",
        "paulscode.sound",
        "scala",
        "space.libs",
        "sun.misc",
        "tv.twitch",
        "watson.cli",
        "wdl",
        "zone.rong",
        "kotlin",
        "kotlinx"
    };

    /** Things totally shouldn't be transformed */
    public static String[] CONTAINS_WHITELIST = {
        "EarlyMixin",
        "LateMixin",
        "Main",
        "betterfps",
        "makamys",
        "mixinbooter",
        "mixinextras",
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
