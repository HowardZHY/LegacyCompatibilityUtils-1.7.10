package space.libs.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;

@SuppressWarnings("all")
public class MacOSTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (name == null || bytes == null) {
            return null;
        }
        if (ClassNameList.Contains(name) || ClassNameList.Startswith(name)) {
            return bytes;
        }
        ClassReader reader = new ClassReader(bytes);
        ClassWriter writer = new ClassWriter(reader, 0);
        ReplaceMACOSVisitor rmv = new ReplaceMACOSVisitor(writer);
        reader.accept(rmv, 0);
        return writer.toByteArray();
    }

    public static class ReplaceMACOSVisitor extends ClassVisitor {

        public ReplaceMACOSVisitor(ClassVisitor cv) {
            super(Opcodes.ASM5, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
            mv = new ReplaceMACOSMethodVisitor(Opcodes.ASM5, mv);
            return mv;
        }

        public static class ReplaceMACOSMethodVisitor extends MethodVisitor {

            public ReplaceMACOSMethodVisitor(int api, MethodVisitor methodVisitor) {
                super(api, methodVisitor);
            }

            @Override
            public void visitFieldInsn(int opcode, String owner, String name, String desc) {
                if (name.equals("MACOS") && desc.contains("net/minecraft/Util$EnumOS")) {
                    mv.visitFieldInsn(opcode, owner, "OSX", desc);
                } else {
                    mv.visitFieldInsn(opcode, owner, name, desc);
                }
            }
        }
    }
}
