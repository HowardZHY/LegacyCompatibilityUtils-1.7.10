package space.libs.asm.remap;

import org.objectweb.asm.*;
import space.libs.asm.*;
import space.libs.asm.visitors.DuplicateMethodVisitor;
import space.libs.core.CompatLoader;

@SuppressWarnings("unused")
public class RemapperUtils {

    public static byte[] transform(String name, String transformedName, byte[] bytes) {
        if (ClassNameList.Contains(name) || ClassNameList.StartsWith(name)) {
            return bytes;
        }
        if (!name.contains(".") && TransformerUtils.isVanillaClass(name)) {
            return bytes;
        }
        if (CompatLoader.isFromLegacyJar(name, 62)) {
            bytes = transformRemap(bytes, new CustomRemapper("1.6.2.srg"), 62);
        } else if (CompatLoader.isFromLegacyJar(name, 64)) {
            bytes = transformRemap(bytes, new CustomRemapper("1.6.4.srg"), 64);
        }
        if (!name.contains(".")) {
            bytes = TransformerUtils.transformSafe(bytes, ClassWriter.COMPUTE_MAXS, DuplicateMethodVisitor.class, ClassReader.SKIP_FRAMES);
        }
        return transformRemap(bytes, new DefaultRemapper(), 1);
    }

    public static byte[] transformRemap(byte[] bytes, DefaultRemapper remapper, int id) {
        ClassReader reader = new ClassReader(bytes);
        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
        reader.accept(new CustomRemappingAdapter(writer, remapper, id), ClassReader.EXPAND_FRAMES);
        return writer.toByteArray();
    }
}
