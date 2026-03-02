package space.libs.asm.visitors;

import org.objectweb.asm.*;

import static org.spongepowered.asm.lib.Opcodes.*;

public class DragonAPIVisitor extends ClassVisitor {

    public DragonAPIVisitor(ClassVisitor cv) {
        super(ASM5, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        if ("readFromNBT".equals(name) || "writeToNBT".equals(name)) {
            return new DragonAPIMethodVisitor(super.visitMethod(access, name, descriptor, signature, exceptions));
        }
        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }

    public static class DragonAPIMethodVisitor extends MethodVisitor {

        public DragonAPIMethodVisitor(MethodVisitor mv) {
            super(ASM5, mv);
        }

        private boolean visited = false;

        @Override
        public void visitCode() {
            if (visited) {
                return;
            }
            visited = true;
            super.visitCode();
            super.visitInsn(RETURN);
            super.visitMaxs(0, 0);
            super.visitEnd();
        }

        @Override
        public void visitEnd() {
            if (!visited) {
                visitCode();
            }
        }
    }
}
