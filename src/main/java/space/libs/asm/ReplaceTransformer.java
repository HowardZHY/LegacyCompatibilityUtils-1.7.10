package space.libs.asm;

import com.google.common.primitives.Bytes;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.commons.Remapper;
import org.objectweb.asm.commons.RemappingClassAdapter;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("all")
public class ReplaceTransformer implements IClassTransformer {

    public static String ATTRIBUTE_OLD = "net/minecraft/entity/ai/attributes/Attribute";

    public static String ATTRIBUTE = "net/minecraft/entity/ai/attributes/IAttribute";

    public static String ENUMHELPER_OLD = "net/minecraftforge/common/EnumHelper";

    public static String ENUMHELPER = "net/minecraftforge/common/util/EnumHelper";

    public static String ICONREGISTER_OLD = "net/minecraft/client/renderer/texture/IconRegister";

    public static String ICONREGISTER = "net/minecraft/client/renderer/texture/IIconRegister";

    public static String TOOLMATERIAL_OLD = "net/minecraft/item/EnumToolMaterial";

    public static String TOOLMATERIAL = "net/minecraft/item/Item$ToolMaterial";

    public static List<String> PACKAGE_PREFIXES = Arrays.asList(
        ATTRIBUTE_OLD,
        ENUMHELPER_OLD,
        ICONREGISTER_OLD,
        TOOLMATERIAL_OLD
    );

    public static List<byte[]> PACKAGE_PREFIXES_RAW = PACKAGE_PREFIXES.stream()
        .map(s -> s.getBytes(StandardCharsets.UTF_8))
        .collect(Collectors.toList());

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (name == null || bytes == null) {
            return bytes;
        }
        if (ClassNameList.Contains(name) || ClassNameList.Startswith(name)) {
            return bytes;
        }
        boolean found = containsAnyPattern(bytes, getPackagePrefixesRaw());
        if (!found) return bytes;
        ClassReader reader = new ClassReader(bytes);
        ClassWriter writer = new ClassWriter(reader, 0);
        RemappingClassAdapter remapAdapter = new ReplaceRemappingAdapter(writer);
        reader.accept(remapAdapter, ClassReader.EXPAND_FRAMES);
        bytes = writer.toByteArray();
        return bytes;
    }

    public static boolean containsAnyPattern(byte[] array, List<byte[]> patterns) {
        if (array == null) {
            return false;
        }
        for (byte[] pattern : patterns) {
            if (Bytes.indexOf(array, pattern) != -1) {
                return true;
            }
        }
        return false;
    }

    public static String getPackagePrefix(int index) {
        return PACKAGE_PREFIXES.get(index);
    }

    public static List<byte[]> getPackagePrefixesRaw() {
        return PACKAGE_PREFIXES_RAW;
    }

    public static class ReplaceRemappingAdapter extends RemappingClassAdapter {
        public ReplaceRemappingAdapter(ClassWriter classWriter) {
            super(classWriter, ReplaceRemapper.INSTANCE);
        }
    }

    public static class ReplaceRemapper extends Remapper {

        public static Remapper INSTANCE = new ReplaceRemapper();

        @Override
        public String map(String typeName) {
            if (typeName.startsWith(ATTRIBUTE_OLD)) {
                return ATTRIBUTE + typeName.substring(ATTRIBUTE_OLD.length());
            }
            if (typeName.startsWith(ENUMHELPER_OLD)) {
                return ENUMHELPER + typeName.substring(ENUMHELPER_OLD.length());
            }
            if (typeName.startsWith(ICONREGISTER_OLD)) {
                return ICONREGISTER + typeName.substring(ICONREGISTER_OLD.length());
            }
            if (typeName.startsWith(TOOLMATERIAL_OLD)) {
                return TOOLMATERIAL + typeName.substring(TOOLMATERIAL_OLD.length());
            }
            return super.map(typeName);
        }
    }

}
