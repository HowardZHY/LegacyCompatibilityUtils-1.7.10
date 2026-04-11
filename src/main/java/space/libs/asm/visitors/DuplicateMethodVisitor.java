package space.libs.asm.visitors;

import org.objectweb.asm.*;
import space.libs.core.ICoreUtils;

import java.util.*;

public class DuplicateMethodVisitor extends ClassVisitor {

    private final Map<String, Integer> methodCounts = new HashMap<>();

    public DuplicateMethodVisitor(ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        String key = name + desc;
        int count = methodCounts.getOrDefault(key, 0);
        methodCounts.put(key, count + 1);
        String newName = name;
        if (count > 0) {
            ICoreUtils.LOGGER.warn("Renaming Founded Duplicated Method: " + name);
            newName = name + "_duped" + count;
        }
        return super.visitMethod(access, newName, desc, signature, exceptions);
    }
}
