package net.minecraft.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@SuppressWarnings("all")
public abstract class ValueObject {
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");
        for (Field field : getClass().getFields()) {
            if (!func_148766_a(field))
                try {
                    stringBuilder.append(field.getName()).append("=").append(field.get(this)).append(" ");
                } catch (IllegalAccessException ignored) {}
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public static boolean func_148766_a(Field paramField) {
        return Modifier.isStatic(paramField.getModifiers());
    }
}
