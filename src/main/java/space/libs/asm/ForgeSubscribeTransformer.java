package space.libs.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;

public class ForgeSubscribeTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (name == null || bytes == null) {
            return bytes;
        }
        if (ClassNameList.Contains(name) || ClassNameList.Startswith(name)) {
            return bytes;
        }
        ClassReader cr = new ClassReader(bytes);
        ClassWriter cw = new ClassWriter(cr, 0);
        ClassVisitor cv = new AnnoVisitor(cw);
        cr.accept(cv, 0);
        return cw.toByteArray();
    }

    public static class AnnoVisitor extends ClassVisitor {

        public AnnoVisitor(ClassVisitor cv) {
            super(Opcodes.ASM5, cv);
        }

        @Override
        public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
            if (desc.equals("Lnet/minecraftforge/event/ForgeSubscribe;")) {
                return super.visitAnnotation("Lcpw/mods/fml/common/eventhandler/SubscribeEvent;", visible);
            }
            if (desc.equals("Lcpw/mods/fml/common/Mod$Init;") || desc.equals("Lcpw/mods/fml/common/Mod$PreInit;") || desc.equals("Lcpw/mods/fml/common/Mod$PostInit;")) {
                return super.visitAnnotation("Lcpw/mods/fml/common/Mod$EventHandler;", visible);
            }
            return super.visitAnnotation(desc, visible);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
            return new MethodAnnoVisitor(mv);
        }

        public static class MethodAnnoVisitor extends MethodVisitor {

            public MethodAnnoVisitor(MethodVisitor mv) {
                super(Opcodes.ASM5, mv);
            }

            @Override
            public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
                if (desc.equals("Lnet/minecraftforge/event/ForgeSubscribe;")) {
                    return super.visitAnnotation("Lcpw/mods/fml/common/eventhandler/SubscribeEvent;", visible);
                }
                if (desc.equals("Lcpw/mods/fml/common/Mod$Init;") || desc.equals("Lcpw/mods/fml/common/Mod$PreInit;") || desc.equals("Lcpw/mods/fml/common/Mod$PostInit;")) {
                    return super.visitAnnotation("Lcpw/mods/fml/common/Mod$EventHandler;", visible);
                }
                return super.visitAnnotation(desc, visible);
            }
        }
    }
}
