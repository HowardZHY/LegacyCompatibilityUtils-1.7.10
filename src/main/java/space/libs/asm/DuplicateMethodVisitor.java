package space.libs.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import space.libs.core.CompatLibCore;

import java.util.HashMap;
import java.util.Map;

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
            CompatLibCore.LOGGER.warn("Renaming Founded Duplicated Method: " + name);
            newName = name + "_duped" + count;
        }
        return super.visitMethod(access, newName, desc, signature, exceptions);
    }
}
