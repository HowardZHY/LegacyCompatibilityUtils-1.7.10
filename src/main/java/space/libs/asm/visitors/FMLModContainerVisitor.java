package space.libs.asm.visitors;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

public class FMLModContainerVisitor extends ClassVisitor {

    public FMLModContainerVisitor(ClassVisitor cv) {
        super(ASM5, cv);
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
            super(ASM5, mv);
        }

        @Override
        public void visitInsn(int opcode) {
            if (opcode == RETURN) {
                mv.visitVarInsn(ALOAD, 0);
                mv.visitLdcInsn("[1.7.2,)");
                mv.visitMethodInsn(INVOKESTATIC, "cpw/mods/fml/common/versioning/VersionParser", "parseRange", "(Ljava/lang/String;)Lcpw/mods/fml/common/versioning/VersionRange;", false);
                mv.visitFieldInsn(PUTFIELD, "cpw/mods/fml/common/FMLModContainer", "minecraftAccepted", "Lcpw/mods/fml/common/versioning/VersionRange;");
            }
            super.visitInsn(opcode);
        }
    }
}
