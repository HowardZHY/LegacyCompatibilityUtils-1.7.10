package net.minecraft.client.mco;

import com.google.gson.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class BackupList {

    public List<Backup> field_148797_a;

    public static BackupList func_148796_a(String p_148796_0_) {
        JsonParser jsonparser = new JsonParser();
        BackupList backuplist = new BackupList();
        backuplist.field_148797_a = new ArrayList<>();
        try {
            JsonElement jsonelement = jsonparser.parse(p_148796_0_).getAsJsonObject().get("backups");
            if (jsonelement.isJsonArray()) {
                for (JsonElement jsonElement : jsonelement.getAsJsonArray()) {
                    backuplist.field_148797_a.add(Backup.func_148777_a(jsonElement));
                }
            }
        }
        catch (JsonIOException | JsonSyntaxException ignored) {}
        return backuplist;
    }
}
