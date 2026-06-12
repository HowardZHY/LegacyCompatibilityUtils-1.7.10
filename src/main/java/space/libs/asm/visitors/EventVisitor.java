package space.libs.asm.visitors;

import org.objectweb.asm.*;

public class EventVisitor extends ClassVisitor {

    public static final String OLD_SUBSCRIBE_TYPE = "Lnet/minecraftforge/event/ForgeSubscribe;";

    public static final String NEW_SUBSCRIBE_TYPE = "Lcpw/mods/fml/common/eventhandler/SubscribeEvent;";

    public static final String OLD_PLAYER_TYPE = "Lcpw/mods/fml/common/network/Player;";

    public static final String NEW_PLAYER_TYPE = "Lspace/libs/interfaces/IPlayer;";

    public static final String OLD_EVENT_TYPE = "Lnet/minecraftforge/event/EventBus;";

    public static final String NEW_EVENT_TYPE = "Lcpw/mods/fml/common/eventhandler/EventBus;";

    public EventVisitor(ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if (OLD_SUBSCRIBE_TYPE.equals(desc)) {
            return super.visitAnnotation(NEW_SUBSCRIBE_TYPE, visible);
        }
        if (desc.equals("Lcpw/mods/fml/common/Mod$Init;") || desc.equals("Lcpw/mods/fml/common/Mod$PreInit;") || desc.equals("Lcpw/mods/fml/common/Mod$PostInit;")) {
            return super.visitAnnotation("Lcpw/mods/fml/common/Mod$EventHandler;", visible);
        }
        return super.visitAnnotation(desc, visible);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        if (OLD_PLAYER_TYPE.equals(desc)) {
            return super.visitField(access, name, NEW_PLAYER_TYPE, signature, value);
        }
        if (OLD_EVENT_TYPE.equals(desc)) {
            return super.visitField(access, name, NEW_EVENT_TYPE, signature, value);
        }
        return super.visitField(access, name, desc, signature, value);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        return new EventMethodVisitor(mv);
    }

    public static class EventMethodVisitor extends MethodVisitor {

        public EventMethodVisitor(MethodVisitor mv) {
            super(Opcodes.ASM5, mv);
        }

        @Override
        public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
            if (OLD_SUBSCRIBE_TYPE.equals(desc)) {
                return super.visitAnnotation(NEW_SUBSCRIBE_TYPE, visible);
            }
            if (desc.equals("Lcpw/mods/fml/common/Mod$Init;") || desc.equals("Lcpw/mods/fml/common/Mod$PreInit;") || desc.equals("Lcpw/mods/fml/common/Mod$PostInit;")) {
                return super.visitAnnotation("Lcpw/mods/fml/common/Mod$EventHandler;", visible);
            }
            return super.visitAnnotation(desc, visible);
        }

        @Override
        public void visitFieldInsn(int opcode, String owner, String name, String desc) {
            if ("MACOS".equals(name) && desc.contains("net/minecraft/Util$EnumOS")) {
                name = "OSX";
            } else if (OLD_PLAYER_TYPE.equals(desc)) {
                desc = NEW_PLAYER_TYPE;
            } else if (OLD_EVENT_TYPE.equals(desc)) {
                desc = NEW_EVENT_TYPE;
            }
            super.visitFieldInsn(opcode, owner, name, desc);
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
            if (owner.equals("cpw/mods/fml/common/network/Player")) {
                owner = "space/libs/interfaces/IPlayer";
            } else if (owner.equals("net/minecraftforge/event/EventBus")) {
                owner = "cpw/mods/fml/common/eventhandler/EventBus";
            }
            super.visitMethodInsn(opcode, owner, name, desc, itf);
        }

        @Override
        public void visitTypeInsn(int opcode, String type) {
            if (type.equals("cpw/mods/fml/common/network/Player")) {
                type = "space/libs/interfaces/IPlayer";
            } else if (type.equals("net/minecraftforge/event/EventBus")) {
                type = "cpw/mods/fml/common/eventhandler/EventBus";
            }
            super.visitTypeInsn(opcode, type);
        }
    }
}
