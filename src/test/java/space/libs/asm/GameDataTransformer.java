package space.libs.asm;

import java.util.Objects;

import net.minecraft.launchwrapper.IClassTransformer;

import org.objectweb.asm.*;
import static org.objectweb.asm.Opcodes.*;

public class GameDataTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (!Objects.equals(name, "cpw.mods.fml.common.registry.GameData")) {
            return bytes;
        }
        ClassReader reader = new ClassReader(bytes);
        ClassWriter writer = new ClassWriter(reader, 0);
        GameDataVisitor cv = new GameDataVisitor(writer);
        reader.accept(cv, 0);
        bytes = writer.toByteArray();
        return bytes;
    }

    public static class GameDataVisitor extends ClassVisitor {
        public GameDataVisitor(ClassVisitor cv) {
            super(Opcodes.ASM5, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
            if (name.equals("registerItem")) {
                mv = new MethodVisitor(Opcodes.ASM5, mv) {
                    @Override
                    public void visitVarInsn(int opcode, int var) {
                        if (opcode == Opcodes.ISTORE && var == 2) {
                            // Insert your code here
                            mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/item/Item", "getUnlocalizedName", "()Ljava/lang/String;", false);
                            mv.visitVarInsn(ASTORE, 2);
                            mv.visitTypeInsn(NEW, "java/lang/Exception");
                            mv.visitInsn(DUP);
                            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Exception", "<init>", "()V", false);
                            mv.visitVarInsn(ASTORE, 3);
                            mv.visitFieldInsn(GETSTATIC, "cpw/mods/fml/common/FMLLog", "severe", "Ljava/lang/String;");
                            mv.visitLdcInsn("Cannot getUnlocalizedName for item " + name);
                            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "concat", "(Ljava/lang/String;)Ljava/lang/String;", false);
                            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Exception", "printStackTrace", "()V", false);
                        }
                        super.visitVarInsn(opcode, var);
                    }
                };
            }
            return mv;
        }
    }
}
