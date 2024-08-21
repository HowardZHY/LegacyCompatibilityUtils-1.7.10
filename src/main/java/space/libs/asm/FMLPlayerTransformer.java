package space.libs.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;

public class FMLPlayerTransformer implements IClassTransformer {

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
        ClassVisitor cv = new FMLPlayerVisitor(cw);
        cr.accept(cv, 0);
        return cw.toByteArray();
    }

    public static class FMLPlayerVisitor extends ClassVisitor {

        public FMLPlayerVisitor(ClassVisitor cv) {
            super(Opcodes.ASM5, cv);
        }

        @Override
        public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
            if (desc.equals("Lcpw/mods/fml/common/network/Player;")) {
                desc = "Lspace/libs/interfaces/IPlayer;";
            }
            return super.visitField(access, name, desc, signature, value);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
            return new PlayerMethodVisitor(mv);
        }

        public static class PlayerMethodVisitor extends MethodVisitor {

            public PlayerMethodVisitor(MethodVisitor mv) {
                super(Opcodes.ASM5, mv);
            }

            @Override
            public void visitFieldInsn(int opcode, String owner, String name, String desc) {
                if (desc.equals("Lcpw/mods/fml/common/network/Player;")) {
                    desc = "Lspace/libs/interfaces/IPlayer;";
                }
                super.visitFieldInsn(opcode, owner, name, desc);
            }

            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
                if (owner.equals("cpw/mods/fml/common/network/Player")) {
                    owner = "space/libs/interfaces/IPlayer";
                }
                super.visitMethodInsn(opcode, owner, name, desc, itf);
            }

            @Override
            public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
                if (desc.equals("Lcpw/mods/fml/common/network/Player;")) {
                    desc = "Lspace/libs/interfaces/IPlayer;";
                }
                super.visitLocalVariable(name, desc, signature, start, end, index);
            }

            @Override
            public void visitTypeInsn(int opcode, String type) {
                if (type.equals("cpw/mods/fml/common/network/Player") && opcode == Opcodes.CHECKCAST) {
                    type = "space/libs/interfaces/IPlayer";
                }
                super.visitTypeInsn(opcode, type);
            }
        }
    }
}
