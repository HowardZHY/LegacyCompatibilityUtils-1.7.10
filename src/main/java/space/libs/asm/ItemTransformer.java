package space.libs.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;

public class ItemTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (name == null || bytes == null) {
            return null;
        }
        if (ClassNameList.Contains(name) || ClassNameList.Startswith(name)) {
            return bytes;
        }
        ClassReader cr = new ClassReader(bytes);
        ClassWriter cw = new ClassWriter(cr, 0);
        ClassVisitor cv = new ItemVisitor(cw);
        cr.accept(cv, 0);
        return cw.toByteArray();
    }

    public static class ItemVisitor extends ClassVisitor {
        public ItemVisitor(ClassVisitor cv) {
            super(Opcodes.ASM5, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            return new MethodVisitor(Opcodes.ASM5, super.visitMethod(access, name, descriptor, signature, exceptions)) {
                @Override
                public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
                    if (descriptor.startsWith("Lnet/minecraft/item/Item") && name.startsWith("field_")) {
                        if (!name.startsWith("field_77700")) {
                            descriptor = "Lnet/minecraft/init/Items;";
                        }
                    }
                    super.visitFieldInsn(opcode, owner, name, descriptor);
                }
            };
        }
    }
}
