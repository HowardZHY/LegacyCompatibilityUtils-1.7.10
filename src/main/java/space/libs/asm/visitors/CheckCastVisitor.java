package space.libs.asm.visitors;

import org.objectweb.asm.*;

import java.util.*;

import static org.objectweb.asm.Opcodes.*;

public class CheckCastVisitor extends ClassVisitor {

    public static final Map<String, String> TYPES = init("net/minecraft/entity/EntityLiving", "net/minecraft/entity/EntityLivingBase");

    public CheckCastVisitor(ClassVisitor cv) {
        super(ASM5, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if ("func_149727_a".equals(name)) {
            return new CheckCastMethodVisitor(mv);
        }
        return mv;
    }

    public static Map<String, String> init(String key, String value) {
        Map<String, String> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    public static class CheckCastMethodVisitor extends MethodVisitor {

        public CheckCastMethodVisitor(MethodVisitor mv) {
            super(ASM5, mv);
        }

        @Override
        public void visitTypeInsn(int opcode, String type) {
            if (opcode == CHECKCAST) {
                String value = TYPES.get(type);
                if (value != null) {
                    type = value;
                }
            }
            super.visitTypeInsn(opcode, type);
        }
    }
}
