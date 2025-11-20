package space.libs.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;

import static org.spongepowered.asm.lib.Opcodes.*;

@SuppressWarnings("unused")
public class EarliestTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (name == null || bytes == null) {
            return bytes;
        }
        if (name.equals("net.minecraftforge.common.config.Configuration")) {
            return TransformerUtils.transform(bytes, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES, ConfigurationVisitor.class, ClassReader.EXPAND_FRAMES);
        } else {
            return bytes;
        }
    }

    public static class ConfigurationVisitor extends ClassVisitor {

        public static final String TARGET = "net/minecraftforge/common/config/Configuration";

        public static final String UTILS = "space/libs/util/forge/ConfigurationUtils";

        public static final String BLOCK = "net/minecraft/block/Block";

        public ConfigurationVisitor(ClassVisitor cv) {
            super(ASM5, cv);
        }

        @Override
        public void visitEnd() {
            addGetBlock(false);
            addGetBlock(true);
            addGetBlockCategory(false);
            addGetBlockCategory(true);
            addGetTerrainBlock();
            addGetBlockInternal();
            addGetItem(false);
            addGetItem(true);
            addGetItemCategory();
            addGetItemFull();
            cv.visitField(ACC_PUBLIC + ACC_STATIC, "configMarkers", "[Z", null, null).visitEnd();
            cv.visitField(ACC_PUBLIC + ACC_STATIC, "ITEM_SHIFT", "I", null, null).visitEnd();
            cv.visitField(ACC_PUBLIC + ACC_STATIC, "MAX_BLOCKS", "I", null, null).visitEnd();
            cv.visitField(ACC_PUBLIC + ACC_STATIC, "CATEGORY_BLOCK", "Ljava/lang/String;", null, null).visitEnd();
            cv.visitField(ACC_PUBLIC + ACC_STATIC, "CATEGORY_ITEM", "Ljava/lang/String;", null, null).visitEnd();
            super.visitEnd();
        }

        public void addGetBlock(boolean comment) {
            MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "getBlock",
                comment ? "(Ljava/lang/String;ILjava/lang/String;)Lnet/minecraftforge/common/config/Property;"
                    : "(Ljava/lang/String;I)Lnet/minecraftforge/common/config/Property;",
                null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETSTATIC, TARGET, "CATEGORY_BLOCK", "Ljava/lang/String;");
            mv.visitVarInsn(ALOAD, 1);
            mv.visitVarInsn(ILOAD, 2);
            if (comment) {
                mv.visitVarInsn(ALOAD, 3);
            } else {
                mv.visitInsn(ACONST_NULL);
            }
            mv.visitIntInsn(SIPUSH, 256);
            mv.visitFieldInsn(GETSTATIC, BLOCK, "blocksList", "[Lnet/minecraft/block/Block;");
            mv.visitInsn(ARRAYLENGTH);
            mv.visitMethodInsn(INVOKEVIRTUAL, TARGET, "getBlockInternal",
                "(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;II)Lnet/minecraftforge/common/config/Property;",
                false);
            mv.visitInsn(ARETURN);
            mv.visitMaxs(7, comment ? 4 : 3);
            mv.visitEnd();
        }

        public void addGetBlockCategory(boolean comment) {
            MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "getBlock",
                comment ? "(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;)Lnet/minecraftforge/common/config/Property;"
                    : "(Ljava/lang/String;Ljava/lang/String;I)Lnet/minecraftforge/common/config/Property;",
                null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitVarInsn(ALOAD, 2);
            mv.visitVarInsn(ILOAD, 3);
            if (comment) {
                mv.visitVarInsn(ALOAD, 4);
            } else {
                mv.visitInsn(ACONST_NULL);
            }
            mv.visitIntInsn(SIPUSH, 256);
            mv.visitFieldInsn(GETSTATIC, BLOCK, "blocksList", "[Lnet/minecraft/block/Block;");
            mv.visitInsn(ARRAYLENGTH);
            mv.visitMethodInsn(INVOKEVIRTUAL, TARGET, "getBlockInternal",
                "(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;II)Lnet/minecraftforge/common/config/Property;",
                false);
            mv.visitInsn(ARETURN);
            mv.visitMaxs(7, comment ? 5 : 4);
            mv.visitEnd();
        }

        public void addGetTerrainBlock() {
            MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "getTerrainBlock",
                "(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;)Lnet/minecraftforge/common/config/Property;",
                null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitVarInsn(ALOAD, 2);
            mv.visitVarInsn(ILOAD, 3);
            mv.visitVarInsn(ALOAD, 4);
            mv.visitInsn(ICONST_0);
            mv.visitIntInsn(SIPUSH, 256);
            mv.visitMethodInsn(INVOKEVIRTUAL, TARGET, "getBlockInternal",
                "(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;II)Lnet/minecraftforge/common/config/Property;",
                false);
            mv.visitInsn(ARETURN);
            mv.visitMaxs(7, 5);
            mv.visitEnd();
        }

        public void addGetBlockInternal() {
            MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "getBlockInternal",
                "(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;II)Lnet/minecraftforge/common/config/Property;",
                null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitVarInsn(ALOAD, 2);
            mv.visitVarInsn(ILOAD, 3);
            mv.visitVarInsn(ALOAD, 4);
            mv.visitVarInsn(ILOAD, 5);
            mv.visitVarInsn(ILOAD, 6);
            mv.visitMethodInsn(INVOKESTATIC, UTILS, "getBlock",
                "(L" + TARGET + ";Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;II)Lnet/minecraftforge/common/config/Property;",
                false);
            mv.visitInsn(ARETURN);
            mv.visitMaxs(7, 7);
            mv.visitEnd();
        }

        public void addGetItem(boolean comment) {
            MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "getItem",
                comment ? "(Ljava/lang/String;ILjava/lang/String;)Lnet/minecraftforge/common/config/Property;"
                    : "(Ljava/lang/String;I)Lnet/minecraftforge/common/config/Property;",
                null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETSTATIC, TARGET, "CATEGORY_ITEM", "Ljava/lang/String;");
            mv.visitVarInsn(ALOAD, 1);
            mv.visitVarInsn(ILOAD, 2);
            if (comment) {
                mv.visitVarInsn(ALOAD, 3);
            } else {
                mv.visitInsn(ACONST_NULL);
            }
            mv.visitMethodInsn(INVOKEVIRTUAL, TARGET, "getItem",
                "(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;)Lnet/minecraftforge/common/config/Property;",
                false);
            mv.visitInsn(ARETURN);
            mv.visitMaxs(5, comment ? 4 : 3);
            mv.visitEnd();
        }

        public void addGetItemCategory() {
            MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "getItem",
                "(Ljava/lang/String;Ljava/lang/String;I)Lnet/minecraftforge/common/config/Property;",
                null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitVarInsn(ALOAD, 2);
            mv.visitVarInsn(ILOAD, 3);
            mv.visitInsn(ACONST_NULL);
            mv.visitMethodInsn(INVOKEVIRTUAL, TARGET, "getItem",
                "(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;)Lnet/minecraftforge/common/config/Property;",
                false);
            mv.visitInsn(ARETURN);
            mv.visitMaxs(5, 4);
            mv.visitEnd();
        }

        public void addGetItemFull() {
            MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "getItem",
                "(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;)Lnet/minecraftforge/common/config/Property;",
                null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitVarInsn(ALOAD, 2);
            mv.visitVarInsn(ILOAD, 3);
            mv.visitVarInsn(ALOAD, 4);
            mv.visitMethodInsn(INVOKESTATIC, UTILS, "getItem",
                "(L" + TARGET + ";Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;)Lnet/minecraftforge/common/config/Property;",
                false);
            mv.visitInsn(ARETURN);
            mv.visitMaxs(5, 5);
            mv.visitEnd();
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String sig, String[] exceptions) {
            MethodVisitor mv = super.visitMethod(access, name, desc, sig, exceptions);
            if (name.equals("<clinit>")) {
                return new StaticVisitor(mv);
            }
            return mv;
        }

        public static class StaticVisitor extends MethodVisitor {

            public StaticVisitor(MethodVisitor mv) {
                super(ASM5, mv);
            }

            @Override
            public void visitInsn(int opcode) {
                if (opcode == RETURN) {
                    mv.visitIntInsn(SIPUSH, 32000);
                    mv.visitIntInsn(NEWARRAY, T_BOOLEAN);
                    mv.visitFieldInsn(PUTSTATIC, TARGET, "configMarkers", "[Z");
                    mv.visitIntInsn(SIPUSH, 256);
                    mv.visitFieldInsn(PUTSTATIC, TARGET, "ITEM_SHIFT", "I");
                    mv.visitIntInsn(SIPUSH, 4096);
                    mv.visitFieldInsn(PUTSTATIC, TARGET, "MAX_BLOCKS", "I");
                    mv.visitLdcInsn("block");
                    mv.visitFieldInsn(PUTSTATIC, TARGET, "CATEGORY_BLOCK", "Ljava/lang/String;");
                    mv.visitLdcInsn("item");
                    mv.visitFieldInsn(PUTSTATIC, TARGET, "CATEGORY_ITEM", "Ljava/lang/String;");
                    mv.visitFieldInsn(GETSTATIC, TARGET, "configMarkers", "[Z");
                    mv.visitInsn(ICONST_0);
                    mv.visitMethodInsn(INVOKESTATIC, "java/util/Arrays", "fill", "([ZZ)V", false);
                }
                super.visitInsn(opcode);
            }
        }
    }

}
