package space.libs.asm.visitors;

import org.objectweb.asm.*;

public class EventVisitor extends ClassVisitor {

    public EventVisitor(ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if (desc.equals("Lnet/minecraftforge/event/ForgeSubscribe;")) {
            return super.visitAnnotation("Lcpw/mods/fml/common/eventhandler/SubscribeEvent;", visible);
        }
        if (desc.equals("Lcpw/mods/fml/common/Mod$Init;") || desc.equals("Lcpw/mods/fml/common/Mod$PreInit;") || desc.equals("Lcpw/mods/fml/common/Mod$PostInit;")) {
            return super.visitAnnotation("Lcpw/mods/fml/common/Mod$EventHandler;", visible);
        }
        return super.visitAnnotation(desc, visible);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        if (desc.equals("Lcpw/mods/fml/common/network/Player;")) {
            return super.visitField(access, name, "Lspace/libs/interfaces/IPlayer;", signature, value);
        }
        if (desc.equals("Lnet/minecraftforge/event/EventBus;")) {
            return super.visitField(access, name, "Lcpw/mods/fml/common/eventhandler/EventBus;", signature, value);
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
            if (desc.equals("Lnet/minecraftforge/event/ForgeSubscribe;")) {
                return super.visitAnnotation("Lcpw/mods/fml/common/eventhandler/SubscribeEvent;", visible);
            }
            if (desc.equals("Lcpw/mods/fml/common/Mod$Init;") || desc.equals("Lcpw/mods/fml/common/Mod$PreInit;") || desc.equals("Lcpw/mods/fml/common/Mod$PostInit;")) {
                return super.visitAnnotation("Lcpw/mods/fml/common/Mod$EventHandler;", visible);
            }
            return super.visitAnnotation(desc, visible);
        }

        @Override
        public void visitFieldInsn(int opcode, String owner, String name, String desc) {
            if (name.equals("MACOS") && desc.contains("net/minecraft/Util$EnumOS")) {
                name = "OSX";
            } else if (desc.equals("Lcpw/mods/fml/common/network/Player;")) {
                desc = "Lspace/libs/interfaces/IPlayer;";
            } else if (desc.equals("Lnet/minecraftforge/event/EventBus;")) {
                desc = "Lcpw/mods/fml/common/eventhandler/EventBus;";
            }
            super.visitFieldInsn(opcode, owner, name, desc);
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
            if (owner.equals("cpw/mods/fml/common/network/Player")) {
                owner = "space/libs/interfaces/IPlayer";
            }
            if (owner.equals("net/minecraftforge/event/EventBus")) {
                owner = "cpw/mods/fml/common/eventhandler/EventBus";
            }
            super.visitMethodInsn(opcode, owner, name, desc, itf);
        }

        @Override
        public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
            if (desc.equals("Lcpw/mods/fml/common/network/Player;")) {
                desc = "Lspace/libs/interfaces/IPlayer;";
            }
            if (desc.equals("net/minecraftforge/event/EventBus")) {
                desc = "cpw/mods/fml/common/eventhandler/EventBus";
            }
            super.visitLocalVariable(name, desc, signature, start, end, index);
        }

        @Override
        public void visitTypeInsn(int opcode, String type) {
            if (type.equals("cpw/mods/fml/common/network/Player") && opcode == Opcodes.CHECKCAST) {
                type = "space/libs/interfaces/IPlayer";
            }
            if (type.equals("net/minecraftforge/event/EventBus")) {
                type = "cpw/mods/fml/common/eventhandler/EventBus";
            }
            super.visitTypeInsn(opcode, type);
        }
    }
}
