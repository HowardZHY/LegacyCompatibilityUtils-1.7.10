package space.libs.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;

public class EventBusTransformer implements IClassTransformer {

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
        ClassVisitor cv = new EventBusVisitor(cw);
        cr.accept(cv, 0);
        return cw.toByteArray();
    }

    public static class EventBusVisitor extends ClassVisitor {

        public EventBusVisitor(ClassVisitor cv) {
            super(Opcodes.ASM5, cv);
        }

        @Override
        public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
            if (desc.equals("Lnet/minecraftforge/event/EventBus;")) {
                desc = "Lcpw/mods/fml/common/eventhandler/EventBus;";
            }
            return super.visitField(access, name, desc, signature, value);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
            return new EventBusMethodVisitor(mv);
        }

        public static class EventBusMethodVisitor extends MethodVisitor {

            public EventBusMethodVisitor(MethodVisitor mv) {
                super(Opcodes.ASM5, mv);
            }

            @Override
            public void visitFieldInsn(int opcode, String owner, String name, String desc) {
                if (desc.equals("Lnet/minecraftforge/event/EventBus;")) {
                    desc = "Lcpw/mods/fml/common/eventhandler/EventBus;";
                }
                super.visitFieldInsn(opcode, owner, name, desc);
            }

            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
                if (owner.equals("net/minecraftforge/event/EventBus")) {
                    owner = "cpw/mods/fml/common/eventhandler/EventBus";
                }
                super.visitMethodInsn(opcode, owner, name, desc, itf);
            }
        }
    }
}
