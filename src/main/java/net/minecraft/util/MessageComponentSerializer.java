package net.minecraft.util;

import com.google.common.collect.Lists;
import com.google.gson.*;
import space.libs.util.MappedName;

import java.lang.reflect.Type;
import java.util.ArrayList;

@SuppressWarnings({"rawtypes", "unused"})
public class MessageComponentSerializer extends ChatStyle.Serializer implements JsonDeserializer, JsonSerializer {

    @MappedName("deserializeComponent")
    public ChatMessageComponent func_111056_a(JsonElement element, Type type, JsonDeserializationContext deserializationContext) {
        ChatMessageComponent component = new ChatMessageComponent();
        JsonObject json = (JsonObject)element;
        JsonElement text = json.get("text");
        JsonElement translate = json.get("translate");
        JsonElement color = json.get("color");
        JsonElement bold = json.get("bold");
        JsonElement italic = json.get("italic");
        JsonElement underlined = json.get("underlined");
        JsonElement obfuscated = json.get("obfuscated");
        if (color != null && color.isJsonPrimitive()) {
            EnumChatFormatting formatting = EnumChatFormatting.getValueByName(color.getAsString());
            if (formatting == null || !formatting.isColor()) {
                throw new JsonParseException("Given color (" + color.getAsString() + ") is not a valid selection");
            }
            component.func_111059_a(formatting);
        }
        if (bold != null && bold.isJsonPrimitive()) {
            component.func_111071_a(bold.getAsBoolean());
        }
        if (italic != null && italic.isJsonPrimitive()) {
            component.func_111063_b(italic.getAsBoolean());
        }
        if (underlined != null && underlined.isJsonPrimitive()) {
            component.func_111081_c(underlined.getAsBoolean());
        }
        if (obfuscated != null && obfuscated.isJsonPrimitive()) {
            component.func_111061_d(obfuscated.getAsBoolean());
        }
        if (text != null) {
            if (text.isJsonArray()) {
                JsonArray jsonArray = text.getAsJsonArray();
                for (JsonElement element1 : jsonArray) {
                    if (element1.isJsonPrimitive()) {
                        component.addText(element1.getAsString());
                        continue;
                    }
                    if (element1.isJsonObject())
                        component.appendComponent(func_111056_a(element1, type, deserializationContext));
                }
            } else if (text.isJsonPrimitive()) {
                component.addText(text.getAsString());
            }
        } else if (translate != null && translate.isJsonPrimitive()) {
            JsonElement using = json.get("using");
            if (using != null) {
                if (using.isJsonArray()) {
                    ArrayList<Object> al = Lists.newArrayList();
                    for (JsonElement element2 : using.getAsJsonArray()) {
                        if (element2.isJsonPrimitive()) {
                            al.add(element2.getAsString());
                            continue;
                        }
                        if (element2.isJsonObject()) {
                            al.add(func_111056_a(element2, type, deserializationContext));
                        }
                    }
                    component.func_111080_a(translate.getAsString(), al.toArray());
                } else if (using.isJsonPrimitive()) {
                    component.func_111080_a(translate.getAsString(), using.getAsString());
                }
            } else {
                component.addKey(translate.getAsString());
            }
        }
        return component;
    }

    @MappedName("serializeComponent")
    public JsonElement func_111055_a(ChatMessageComponent component, Type type, JsonSerializationContext context) {
        JsonObject json = new JsonObject();
        if (component.func_111065_a() != null)
            json.addProperty("color", component.func_111065_a().getFriendlyName());
        if (component.func_111058_b() != null)
            json.addProperty("bold", component.func_111058_b());
        if (component.func_111064_c() != null)
            json.addProperty("italic", component.func_111064_c());
        if (component.func_111067_d() != null)
            json.addProperty("underlined", component.func_111067_d());
        if (component.func_111076_e() != null)
            json.addProperty("obfuscated", component.func_111076_e());
        if (component.func_111075_f() != null) {
            json.addProperty("text", component.func_111075_f());
        } else if (component.func_111074_g() != null) {
            json.addProperty("translate", component.func_111074_g());
            if (component.func_111069_h() != null && !component.func_111069_h().isEmpty())
                json.add("using", func_111057_b(component, type, context));
        } else if (component.func_111069_h() != null && !component.func_111069_h().isEmpty()) {
            json.add("text", func_111057_b(component, type, context));
        }
        return json;
    }

    @MappedName("serializeComponentChildren")
    public JsonArray func_111057_b(ChatMessageComponent component, Type type, JsonSerializationContext context) {
        JsonArray jsonArray = new JsonArray();
        for (ChatMessageComponent chatMessageComponent : component.func_111069_h()) {
            if (chatMessageComponent.func_111075_f() != null) {
                jsonArray.add(new JsonPrimitive(chatMessageComponent.func_111075_f()));
                continue;
            }
            jsonArray.add(func_111055_a(chatMessageComponent, type, context));
        }
        return jsonArray;
    }

    @Override
    public ChatMessageComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        return this.func_111056_a(json, typeOfT, context);
    }

    public JsonElement serialize(ChatMessageComponent src, Type typeOfSrc, JsonSerializationContext context) {
        return this.func_111055_a(src, typeOfSrc, context);
    }

    @Override
    public JsonElement serialize(ChatStyle src, Type typeOfSrc, JsonSerializationContext context) {
        return super.serialize(src, typeOfSrc, context);
    }

    @Override
    public JsonElement serialize(Object src, Type typeOfSrc, JsonSerializationContext context) {
        return serialize((ChatStyle) src, typeOfSrc, context);
    }
}
