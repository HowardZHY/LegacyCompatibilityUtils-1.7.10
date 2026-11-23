package net.minecraft.util;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import space.libs.util.MappedName;

@SuppressWarnings("UnusedReturnValue")
public class ChatMessageComponent extends ChatStyle {

    public static final Gson field_111089_a = (new GsonBuilder()).registerTypeAdapter(ChatMessageComponent.class, new MessageComponentSerializer()).create();

    @MappedName("color")
    public EnumChatFormatting field_111087_b;

    @MappedName("bold")
    public Boolean field_111088_c;

    @MappedName("italic")
    public Boolean field_111085_d;

    @MappedName("underline")
    public Boolean field_111086_e;

    @MappedName("obfuscated")
    public Boolean field_111083_f;

    @MappedName("text")
    public String field_111084_g;

    @MappedName("translationKey")
    public String field_111090_h;

    public List<ChatMessageComponent> field_111091_i;

    public ChatMessageComponent() {
        super();
    }

    public ChatMessageComponent(ChatMessageComponent component) {
        super();
        this.func_111059_a(component.field_111087_b);
        this.func_111071_a(component.field_111088_c);
        this.func_111063_b(component.field_111085_d);
        this.func_111081_c(component.field_111086_e);
        this.func_111061_d(component.field_111083_f);
        this.field_111084_g = component.field_111084_g;
        this.field_111090_h = component.field_111090_h;
        this.field_111091_i = (component.field_111091_i == null) ? null : Lists.newArrayList(component.field_111091_i);
    }

    public ChatMessageComponent func_111059_a(EnumChatFormatting formatting) {
        if (formatting != null && !formatting.isColor()) {
            throw new IllegalArgumentException("Argument is not a valid color!");
        }
        super.setColor(formatting);
        this.field_111087_b = formatting;
        return this;
    }

    @MappedName("getColor")
    public EnumChatFormatting func_111065_a() {
        return this.field_111087_b;
    }

    public ChatMessageComponent func_111071_a(Boolean bold) {
        super.setBold(bold);
        this.field_111088_c = bold;
        return this;
    }

    @MappedName("isBold")
    public Boolean func_111058_b() {
        return this.field_111088_c;
    }

    public ChatMessageComponent func_111063_b(Boolean italic) {
        super.setItalic(italic);
        this.field_111085_d = italic;
        return this;
    }

    @MappedName("isItalic")
    public Boolean func_111064_c() {
        return this.field_111085_d;
    }

    public ChatMessageComponent func_111081_c(Boolean underline) {
        super.setUnderlined(underline);
        this.field_111086_e = underline;
        return this;
    }

    @MappedName("isUnderline")
    public Boolean func_111067_d() {
        return this.field_111086_e;
    }

    public ChatMessageComponent func_111061_d(Boolean obf) {
        super.setObfuscated(obf);
        this.field_111083_f = obf;
        return this;
    }

    @MappedName("isObfuscated")
    public Boolean func_111076_e() {
        return this.field_111083_f;
    }

    @MappedName("getText")
    public String func_111075_f() {
        return this.field_111084_g;
    }

    @MappedName("getTranslationKey")
    public String func_111074_g() {
        return this.field_111090_h;
    }

    @MappedName("getSubComponents")
    public List<ChatMessageComponent> func_111069_h() {
        return this.field_111091_i;
    }

    @MappedName("appendComponent")
    public ChatMessageComponent appendComponent(ChatMessageComponent component) {
        if (this.field_111084_g == null && this.field_111090_h == null) {
            if (this.field_111091_i != null) {
                this.field_111091_i.add(component);
            } else {
                this.field_111091_i = Lists.newArrayList(component);
            }
        } else {
            this.field_111091_i = Lists.newArrayList(new ChatMessageComponent(this), component);
            this.field_111084_g = null;
            this.field_111090_h = null;
        }
        return this;
    }

    @MappedName("addText")
    public ChatMessageComponent addText(String text) {
        if (this.field_111084_g == null && this.field_111090_h == null) {
            if (this.field_111091_i != null) {
                this.field_111091_i.add(func_111066_d(text));
            } else {
                this.field_111084_g = text;
            }
        } else {
            this.field_111091_i = Lists.newArrayList(new ChatMessageComponent(this), func_111066_d(text));
            this.field_111084_g = null;
            this.field_111090_h = null;
        }
        return this;
    }

    @MappedName("addKey")
    public ChatMessageComponent addKey(String key) {
        if (this.field_111084_g == null && this.field_111090_h == null) {
            if (this.field_111091_i != null) {
                this.field_111091_i.add(func_111077_e(key));
            } else {
                this.field_111090_h = key;
            }
        } else {
            this.field_111091_i = Lists.newArrayList(new ChatMessageComponent(this), func_111077_e(key));
            this.field_111084_g = null;
            this.field_111090_h = null;
        }
        return this;
    }

    @MappedName("addFormatted")
    public ChatMessageComponent func_111080_a(String msg, Object... objects) {
        if (this.field_111084_g == null && this.field_111090_h == null) {
            if (this.field_111091_i != null) {
                this.field_111091_i.add(func_111082_b(msg, objects));
            } else {
                this.field_111090_h = msg;
                this.field_111091_i = Lists.newArrayList();
                for (Object o : objects) {
                    if (o instanceof ChatMessageComponent) {
                        this.field_111091_i.add((ChatMessageComponent)o);
                    } else {
                        this.field_111091_i.add(func_111066_d(o.toString()));
                    }
                }
            }
        } else {
            this.field_111091_i = Lists.newArrayList(new ChatMessageComponent(this), func_111082_b(msg, objects));
            this.field_111084_g = null;
            this.field_111090_h = null;
        }
        return this;
    }

    public String toString() {
        return func_111068_a(false);
    }

    @MappedName("toStringWithFormatting")
    public String func_111068_a(boolean format) {
        return func_111070_a(format, null, false, false, false, false);
    }

    @MappedName("toStringWithDefaultFormatting")
    public String func_111070_a(boolean format, EnumChatFormatting formatting, boolean bold, boolean italic, boolean underline, boolean obf) {
        StringBuilder builder = new StringBuilder();
        EnumChatFormatting formatting1 = (this.field_111087_b == null) ? formatting : this.field_111087_b;
        boolean isBold = (this.field_111088_c == null) ? bold : this.field_111088_c;
        boolean isItalic = (this.field_111085_d == null) ? italic : this.field_111085_d;
        boolean isUnderline = (this.field_111086_e == null) ? underline : this.field_111086_e;
        boolean isObf = (this.field_111083_f == null) ? obf : this.field_111083_f;
        if (this.field_111090_h != null) {
            if (format) {
                func_111060_a(builder, formatting1, isBold, isItalic, isUnderline, isObf);
            }
            if (this.field_111091_i != null) {
                String[] as = new String[this.field_111091_i.size()];
                for (byte b = 0; b < this.field_111091_i.size(); b++)
                    as[b] = this.field_111091_i.get(b).func_111070_a(format, formatting1, isBold, isItalic, isUnderline, isObf);
                builder.append(StatCollector.translateToLocalFormatted(this.field_111090_h, (Object[])as));
            } else {
                builder.append(StatCollector.translateToLocal(this.field_111090_h));
            }
        } else if (this.field_111084_g != null) {
            if (format) {
                func_111060_a(builder, formatting1, isBold, isItalic, isUnderline, isObf);
            }
            builder.append(this.field_111084_g);
        } else if (this.field_111091_i != null) {
            for (ChatMessageComponent chatMessageComponent : this.field_111091_i) {
                if (format) {
                    func_111060_a(builder, formatting1, isBold, isItalic, isUnderline, isObf);
                }
                builder.append(chatMessageComponent.func_111070_a(format, formatting1, isBold, isItalic, isUnderline, isObf));
            }
        }
        return builder.toString();
    }

    @MappedName("appendFormattingToString")
    public static void func_111060_a(StringBuilder builder, EnumChatFormatting formatting, boolean bold, boolean italic, boolean underline, boolean obf) {
        if (formatting != null) {
            builder.append(formatting);
        } else if (bold || italic || underline || obf) {
            builder.append(EnumChatFormatting.RESET);
        }
        if (bold) {
            builder.append(EnumChatFormatting.BOLD);
        }
        if (italic) {
            builder.append(EnumChatFormatting.ITALIC);
        }
        if (underline) {
            builder.append(EnumChatFormatting.UNDERLINE);
        }
        if (obf) {
            builder.append(EnumChatFormatting.OBFUSCATED);
        }
    }

    @SuppressWarnings("unused")
    @MappedName("createFromJson")
    public static ChatMessageComponent func_111078_c(String msg) {
        try {
            return field_111089_a.fromJson(msg, ChatMessageComponent.class);
        } catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Deserializing Message");
            CrashReportCategory crashReportCategory = crashReport.makeCategory("Serialized Message");
            crashReportCategory.addCrashSection("JSON string", msg);
            throw new ReportedException(crashReport);
        }
    }

    @MappedName("createFromText")
    public static ChatMessageComponent func_111066_d(String text) {
        ChatMessageComponent component = new ChatMessageComponent();
        component.addText(text);
        return component;
    }

    @MappedName("createFromTranslationKey")
    public static ChatMessageComponent func_111077_e(String key) {
        ChatMessageComponent component = new ChatMessageComponent();
        component.addKey(key);
        return component;
    }

    @MappedName("createFromTranslationWithSubstitutions")
    public static ChatMessageComponent func_111082_b(String msg, Object... objects) {
        ChatMessageComponent component = new ChatMessageComponent();
        component.func_111080_a(msg, objects);
        return component;
    }

    @MappedName("toJson")
    public String func_111062_i() {
        return field_111089_a.toJson(this);
    }
}
