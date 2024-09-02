package space.libs.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;

public class FMLModContainerTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (name == null || bytes == null) {
            return bytes;
        }
        if (!name.equals("cpw.mods.fml.common.FMLModContainer")) {
            return bytes;
        }
        ClassReader cr = new ClassReader(bytes);
        ClassWriter cw = new ClassWriter(cr, 0);
        ClassVisitor cv = new FMLModContainerVisitor(cw);
        cr.accept(cv, 0);
        return cw.toByteArray();
    }

    public static class FMLModContainerVisitor extends ClassVisitor {

        public FMLModContainerVisitor(ClassVisitor cv) {
            super(Opcodes.ASM5, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
            if (name.equals("bindMetadata")) {
                return new BindMetadataMethodVisitor(mv);
            }
            return mv;
        }

        public static class BindMetadataMethodVisitor extends MethodVisitor {

            public BindMetadataMethodVisitor(MethodVisitor mv) {
                super(Opcodes.ASM5, mv);
            }

            @Override
            public void visitInsn(int opcode) {
                if (opcode == Opcodes.RETURN) {
                    mv.visitVarInsn(Opcodes.ALOAD, 0);
                    mv.visitLdcInsn("[1.7.2,)");
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, "cpw/mods/fml/common/versioning/VersionParser", "parseRange", "(Ljava/lang/String;)Lcpw/mods/fml/common/versioning/VersionRange;", false);
                    mv.visitFieldInsn(Opcodes.PUTFIELD, "cpw/mods/fml/common/FMLModContainer", "minecraftAccepted", "Lcpw/mods/fml/common/versioning/VersionRange;");
                }
                super.visitInsn(opcode);
            }
        }
    }
}
