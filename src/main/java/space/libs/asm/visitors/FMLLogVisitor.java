package space.libs.asm.visitors;

import org.objectweb.asm.*;

import static org.objectweb.asm.Opcodes.*;

public class FMLLogVisitor extends ClassVisitor {

    private static final String TARGET = "cpw/mods/fml/common/FMLLog";

    private static final String LOGGER_UTILS = "space/libs/util/LoggerUtils";

    private static final String LOGGER_TYPE = "Ljava/util/logging/Logger;";

    private static final String LEVEL_TYPE0 = "Ljava/util/logging/Level;";

    private static final String LEVEL_TYPE1 = "Lorg/apache/logging/log4j/Level;";

    private static final String S0 = "(Ljava/lang/String;";

    private static final String S1 = "Ljava/lang/String;[Ljava/lang/Object;)V";

    private static final String S2 = "Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V";

    private static final String TO_LEVEL_DESC = "(" + LEVEL_TYPE0 + ")" + LEVEL_TYPE1;

    public FMLLogVisitor(ClassVisitor cv) {
        super(ASM5, cv);
    }

    @Override
    public void visitEnd() {
        addLogWithChannel();
        addLog();
        addLogWithChannelAndThrowable();
        addLogWithThrowable();
        addGetLogger();
        super.visitEnd();
    }

    private void addLogWithChannel() {
        MethodVisitor mv = cv.visitMethod(ACC_PUBLIC | ACC_STATIC | ACC_VARARGS, "log",
            S0 + LEVEL_TYPE0 + S1, null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitMethodInsn(INVOKESTATIC, LOGGER_UTILS, "toLevel", TO_LEVEL_DESC, false);
        mv.visitVarInsn(ALOAD, 2);
        mv.visitVarInsn(ALOAD, 3);
        mv.visitMethodInsn(INVOKESTATIC, TARGET, "log",
            S0 + LEVEL_TYPE1 + S1, false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(4, 4);
        mv.visitEnd();
    }

    private void addLog() {
        MethodVisitor mv = cv.visitMethod(ACC_PUBLIC | ACC_STATIC | ACC_VARARGS, "log",
            "(" + LEVEL_TYPE0 + S1, null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESTATIC, LOGGER_UTILS, "toLevel", TO_LEVEL_DESC, false);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitVarInsn(ALOAD, 2);
        mv.visitMethodInsn(INVOKESTATIC, TARGET, "log",
            "(" + LEVEL_TYPE1 + S1, false
        );
        mv.visitInsn(RETURN);
        mv.visitMaxs(3, 3);
        mv.visitEnd();
    }

    private void addLogWithChannelAndThrowable() {
        MethodVisitor mv = cv.visitMethod(ACC_PUBLIC | ACC_STATIC | ACC_VARARGS, "log",
            S0 + LEVEL_TYPE0 + S2, null, null
        );
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitMethodInsn(INVOKESTATIC, LOGGER_UTILS, "toLevel", TO_LEVEL_DESC, false);
        mv.visitVarInsn(ALOAD, 2);
        mv.visitVarInsn(ALOAD, 3);
        mv.visitVarInsn(ALOAD, 4);
        mv.visitMethodInsn(INVOKESTATIC, TARGET, "log",
            S0 + LEVEL_TYPE1 + S2, false
        );
        mv.visitInsn(RETURN);
        mv.visitMaxs(5, 5);
        mv.visitEnd();
    }

    private void addLogWithThrowable() {
        MethodVisitor mv = cv.visitMethod(ACC_PUBLIC | ACC_STATIC | ACC_VARARGS, "log",
            "(" + LEVEL_TYPE0 + S2, null, null
        );
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESTATIC, LOGGER_UTILS, "toLevel", TO_LEVEL_DESC, false);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitVarInsn(ALOAD, 2);
        mv.visitVarInsn(ALOAD, 3);
        mv.visitMethodInsn(INVOKESTATIC, TARGET, "log",
            "(" + LEVEL_TYPE1 + S2, false
        );
        mv.visitInsn(RETURN);
        mv.visitMaxs(4, 4);
        mv.visitEnd();
    }

    private void addGetLogger() {
        MethodVisitor mv = cv.visitMethod(ACC_PUBLIC | ACC_STATIC, "getLogger", "()" + LOGGER_TYPE, null, null);
        mv.visitCode();
        mv.visitFieldInsn(GETSTATIC, LOGGER_UTILS, "INSTANCE", LOGGER_TYPE);
        mv.visitInsn(ARETURN);
        mv.visitMaxs(1, 0);
        mv.visitEnd();
    }

}
