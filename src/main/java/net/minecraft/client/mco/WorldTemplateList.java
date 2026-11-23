package net.minecraft.client.mco;

import com.google.gson.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.ValueObject;

@SideOnly(Side.CLIENT)
public class WorldTemplateList extends ValueObject {

    public List<WorldTemplate> field_148782_a;

    public static WorldTemplateList func_148781_a(String p_148781_0_) {
        WorldTemplateList worldtemplatelist = new WorldTemplateList();
        worldtemplatelist.field_148782_a = new ArrayList<>();
        try {
            JsonParser jsonparser = new JsonParser();
            JsonObject jsonobject = jsonparser.parse(p_148781_0_).getAsJsonObject();
            if (jsonobject.get("templates").isJsonArray()) {
                for (JsonElement jsonElement : jsonobject.get("templates").getAsJsonArray()) {
                    worldtemplatelist.field_148782_a.add(WorldTemplate.func_148783_a(jsonElement.getAsJsonObject()));
                }
            }
        }
        catch (JsonIOException | JsonSyntaxException ignored) {}
        return worldtemplatelist;
    }
}
