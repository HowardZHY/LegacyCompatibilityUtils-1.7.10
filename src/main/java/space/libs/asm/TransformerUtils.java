package space.libs.asm;

import org.objectweb.asm.*;

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
        int l = name.length();
        if (l > 3) {
            return false;
        }
        for (int i = 0; i < l; i++) {
            char c = name.charAt(i);
            if (c < 'a' || c > 'z') {
                return false;
            }
        }
        return true;
    }
}
