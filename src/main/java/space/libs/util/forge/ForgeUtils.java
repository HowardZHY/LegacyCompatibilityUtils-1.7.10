package space.libs.util.forge;

import cpw.mods.fml.common.ModMetadata;

public abstract class ForgeUtils {

    public static ModMetadata createModData(String modID, String name, String version, String author) {
        ModMetadata data = new ModMetadata();
        data.modId = modID;
        data.name = name;
        data.version = version;
        data.authorList.add(author);
        return data;
    }

    public static String getCallerClass(int index) {
        StackTraceElement[] stack = new Throwable().getStackTrace();
        return stack[index].getClassName();
    }
}
