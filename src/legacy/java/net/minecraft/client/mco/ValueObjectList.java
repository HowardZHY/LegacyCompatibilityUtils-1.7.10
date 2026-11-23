package net.minecraft.client.mco;

import com.google.gson.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.ValueObject;

@SideOnly(Side.CLIENT)
public class ValueObjectList extends ValueObject {

    public List<McoServer> field_148772_a;

    public static ValueObjectList func_148771_a(String p_148771_0_) {
        ValueObjectList valueobjectlist = new ValueObjectList();
        valueobjectlist.field_148772_a = new ArrayList<>();
        try {
            JsonParser jsonparser = new JsonParser();
            JsonObject jsonobject = jsonparser.parse(p_148771_0_).getAsJsonObject();
            if (jsonobject.get("servers").isJsonArray()) {
                JsonArray jsonarray = jsonobject.get("servers").getAsJsonArray();
                for (JsonElement jsonElement : jsonarray) {
                    valueobjectlist.field_148772_a.add(McoServer.func_148802_a(jsonElement.getAsJsonObject()));
                }
            }
        }
        catch (JsonIOException | JsonSyntaxException ignored) {}
        return valueobjectlist;
    }
}
