package space.libs.asm.visitors;

import org.objectweb.asm.*;
import static org.objectweb.asm.Opcodes.*;

public class ModContainerFactoryVisitor extends ClassVisitor {

    public ModContainerFactoryVisitor(ClassVisitor visitor) {
        super(ASM5, visitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if ("build".equals(name) && "(Lcpw/mods/fml/common/discovery/asm/ASMModParser;Ljava/io/File;Lcpw/mods/fml/common/discovery/ModCandidate;)Lcpw/mods/fml/common/ModContainer;".equals(desc)) {
            return new BuildMethodVisitor(mv);
        }
        return mv;
    }

    public static class BuildMethodVisitor extends MethodVisitor {

        Label continueLabel = new Label();

        public BuildMethodVisitor(MethodVisitor mv) {
            super(ASM5, mv);
        }

        @Override
        public void visitCode() {
            super.visitCode();
            int classNameLocal = 4;
            visitVarInsn(ALOAD, 1);
            visitMethodInsn(INVOKEVIRTUAL, "cpw/mods/fml/common/discovery/asm/ASMModParser", "getASMType", "()Lorg/objectweb/asm/Type;", false);
            visitMethodInsn(INVOKEVIRTUAL, "org/objectweb/asm/Type", "getClassName", "()Ljava/lang/String;", false);
            visitVarInsn(ASTORE, classNameLocal);
            visitVarInsn(ALOAD, 1);
            visitVarInsn(ALOAD, 3);
            visitMethodInsn(INVOKEVIRTUAL, "cpw/mods/fml/common/discovery/ModCandidate", "getRememberedBaseMods", "()Ljava/util/List;", false);
            visitMethodInsn(INVOKEVIRTUAL, "cpw/mods/fml/common/discovery/asm/ASMModParser", "isBaseMod", "(Ljava/util/List;)Z", false);
            visitJumpInsn(IFEQ, continueLabel);
            visitVarInsn(ALOAD, classNameLocal);
            visitMethodInsn(INVOKESTATIC, "space/libs/util/forge/ModLoadingUtils", "find", "(Ljava/lang/String;)Z", false);
            visitJumpInsn(IFEQ, continueLabel);
            visitTypeInsn(NEW, "cpw/mods/fml/common/modloader/ModLoaderModContainer");
            visitInsn(DUP);
            visitVarInsn(ALOAD, classNameLocal);
            visitVarInsn(ALOAD, 2);
            visitVarInsn(ALOAD, 1);
            visitMethodInsn(INVOKEVIRTUAL, "cpw/mods/fml/common/discovery/asm/ASMModParser", "getBaseModProperties", "()Ljava/lang/String;", false);
            visitMethodInsn(INVOKESPECIAL, "cpw/mods/fml/common/modloader/ModLoaderModContainer", "<init>", "(Ljava/lang/String;Ljava/io/File;Ljava/lang/String;)V", false);
            visitInsn(ARETURN);
            visitLabel(continueLabel);
        }
    }
}
