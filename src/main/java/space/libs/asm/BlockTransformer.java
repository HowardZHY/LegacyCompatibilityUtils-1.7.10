package space.libs.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;

public class BlockTransformer implements IClassTransformer {

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
        ClassVisitor cv = new BlockVisitor(cw);
        cr.accept(cv, 0);
        return cw.toByteArray();
    }

    public static class BlockVisitor extends ClassVisitor {
        public BlockVisitor(ClassVisitor cv) {
            super(Opcodes.ASM5, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            return new MethodVisitor(Opcodes.ASM5, super.visitMethod(access, name, descriptor, signature, exceptions)) {
                @Override
                public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
                    if (owner.equals("net/minecraft/block/Block") && descriptor.startsWith("Lnet/minecraft/block/Block") && (!descriptor.contains("$")) && name.startsWith("field_")) {
                        owner = "net/minecraft/init/Blocks";
                    }
                    super.visitFieldInsn(opcode, owner, name, descriptor);
                }
            };
        }
    }
}
