package space.libs.asm;

import org.objectweb.asm.*;

@SuppressWarnings("unused")
public class ClassWriterSafe extends ClassWriter {

    public static final String OBJECT = "java/lang/Object";

    public ClassWriterSafe(int flags) {
        super(flags);
    }

    public ClassWriterSafe(ClassReader cr, int flags) {
        super(cr, flags);
    }

    @Override
    protected String getCommonSuperClass(String type1, String type2) {
        try {
            ClassLoader cl = ClassWriterSafe.class.getClassLoader();
            Class<?> c1 = Class.forName(type1.replace('/', '.'), false, cl);
            Class<?> c2 = Class.forName(type2.replace('/', '.'), false, cl);
            if (c1.isAssignableFrom(c2)) {
                return type1;
            } else if (c2.isAssignableFrom(c1)) {
                return type2;
            } else if (c1.isInterface() || c2.isInterface()) {
                return OBJECT;
            } else do {
                c1 = c1.getSuperclass();
            } while (!c1.isAssignableFrom(c2));
            return c1.getName().replace('.', '/');
        } catch (Throwable t) {
            return OBJECT;
        }
    }
}
