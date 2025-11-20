package space.libs.asm;

import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.transformers.MixinClassWriter;

public class TransformerUtils {

    public static byte[] transform(byte[] bytes, int writerFlags, Class<? extends org.objectweb.asm.ClassVisitor> visitor, int acceptFlags) {
        try {
            org.objectweb.asm.ClassReader cr = new org.objectweb.asm.ClassReader(bytes);
            org.objectweb.asm.ClassWriter cw = new org.objectweb.asm.ClassWriter(cr, writerFlags);
            org.objectweb.asm.ClassVisitor cv = visitor.getDeclaredConstructor(org.objectweb.asm.ClassVisitor.class).newInstance(cw);
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
            ClassWriter cw = new MixinClassWriter(cr, writerFlags);
            ClassVisitor cv = visitor.getDeclaredConstructor(ClassVisitor.class).newInstance(cw);
            cr.accept(cv, acceptFlags);
            return cw.toByteArray();
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
