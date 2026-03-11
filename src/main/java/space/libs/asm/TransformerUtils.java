package space.libs.asm;

import org.objectweb.asm.*;

import java.util.Locale;

public class TransformerUtils {

    public static byte[] transform(byte[] bytes, int writerFlags, Class<? extends ClassVisitor> visitor, int acceptFlags) {
        try {
            ClassReader cr = new ClassReader(bytes);
            ClassWriter cw = new ClassWriter(cr, writerFlags);
            ClassVisitor cv = visitor.getDeclaredConstructor(ClassVisitor.class).newInstance(cw);
            cr.accept(cv, acceptFlags);
            return cw.toByteArray();
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static byte[] transformSafe(byte[] bytes, int writerFlags, Class<? extends ClassVisitor> visitor, int acceptFlags) {
        try {
            ClassReader cr = new ClassReader(bytes);
            ClassWriter cw = new ClassWriterSafe(cr, writerFlags);
            ClassVisitor cv = visitor.getDeclaredConstructor(ClassVisitor.class).newInstance(cw);
            cr.accept(cv, acceptFlags);
            return cw.toByteArray();
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static boolean isVanillaClass(String name) {
        return (name.length() < 4 && name.equals(name.toLowerCase(Locale.ENGLISH)));
    }
}
