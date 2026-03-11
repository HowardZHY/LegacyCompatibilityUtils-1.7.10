package space.libs.util;

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

}
