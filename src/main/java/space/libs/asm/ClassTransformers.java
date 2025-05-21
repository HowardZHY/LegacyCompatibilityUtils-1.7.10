package space.libs.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.transformers.MixinClassWriter;

@SuppressWarnings("unused")
public class ClassTransformers implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (name == null || bytes == null) {
            return bytes;
        }
        if (name.startsWith("cpw")) {
            if (name.equals("cpw.mods.fml.common.FMLModContainer")) {
                ClassReader r = new ClassReader(bytes);
                ClassWriter w = new MixinClassWriter(r, ClassWriter.COMPUTE_MAXS);
                ClassVisitor v = new FMLModContainerVisitor(w);
                r.accept(v, 0);
                return w.toByteArray();
            }
            if (name.equals("cpw.mods.fml.common.ModContainerFactory")) {
                ClassReader reader = new ClassReader(bytes);
                ClassWriter writer = new MixinClassWriter(reader, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
                ClassVisitor visitor = new ModContainerFactoryVisitor(writer);
                reader.accept(visitor, ClassReader.EXPAND_FRAMES);
                return writer.toByteArray();
            }
        }
        if (ClassNameList.Contains(name) || ClassNameList.Startswith(name)) {
            return bytes;
        }
        ClassReader cr = new ClassReader(bytes);
        ClassWriter cw = new MixinClassWriter(cr, 0);
        ClassVisitor cv = new EventVisitor(cw);
        cr.accept(cv, 0);
        return cw.toByteArray();
    }

    public static class EventVisitor extends ClassVisitor {

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

    public static class FMLModContainerVisitor extends ClassVisitor {

        public FMLModContainerVisitor(ClassVisitor cv) {
            super(Opcodes.ASM5, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
            if (name.equals("bindMetadata")) {
                return new BindMetadataMethodVisitor(mv);
            }
            return mv;
        }

        public static class BindMetadataMethodVisitor extends MethodVisitor {

            public BindMetadataMethodVisitor(MethodVisitor mv) {
                super(Opcodes.ASM5, mv);
            }

            @Override
            public void visitInsn(int opcode) {
                if (opcode == Opcodes.RETURN) {
                    mv.visitVarInsn(Opcodes.ALOAD, 0);
                    mv.visitLdcInsn("[1.7.2,)");
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, "cpw/mods/fml/common/versioning/VersionParser", "parseRange", "(Ljava/lang/String;)Lcpw/mods/fml/common/versioning/VersionRange;", false);
                    mv.visitFieldInsn(Opcodes.PUTFIELD, "cpw/mods/fml/common/FMLModContainer", "minecraftAccepted", "Lcpw/mods/fml/common/versioning/VersionRange;");
                }
                super.visitInsn(opcode);
            }
        }
    }

    public static class ModContainerFactoryVisitor extends ClassVisitor {

        public ModContainerFactoryVisitor(ClassVisitor visitor) {
            super(Opcodes.ASM5, visitor);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
            if ("build".equals(name) && "(Lcpw/mods/fml/common/discovery/asm/ASMModParser;Ljava/io/File;Lcpw/mods/fml/common/discovery/ModCandidate;)Lcpw/mods/fml/common/ModContainer;".equals(desc)) {
                return new BuildMethodVisitor(mv, access, name, desc);
            }
            return mv;
        }

        public static class BuildMethodVisitor extends MethodVisitor {

            Label continueLabel = new Label();

            public BuildMethodVisitor(MethodVisitor mv, int access, String name, String desc) {
                super(Opcodes.ASM5, mv);
            }

            @Override
            public void visitCode() {
                super.visitCode();
                int classNameLocal = 4;
                visitVarInsn(Opcodes.ALOAD, 1);
                visitMethodInsn(Opcodes.INVOKEVIRTUAL, "cpw/mods/fml/common/discovery/asm/ASMModParser", "getASMType", "()Lorg/objectweb/asm/Type;", false);
                visitMethodInsn(Opcodes.INVOKEVIRTUAL, "org/objectweb/asm/Type", "getClassName", "()Ljava/lang/String;", false);
                visitVarInsn(Opcodes.ASTORE, classNameLocal);
                visitVarInsn(Opcodes.ALOAD, 1);
                visitVarInsn(Opcodes.ALOAD, 3);
                visitMethodInsn(Opcodes.INVOKEVIRTUAL, "cpw/mods/fml/common/discovery/ModCandidate", "getRememberedBaseMods", "()Ljava/util/List;", false);
                visitMethodInsn(Opcodes.INVOKEVIRTUAL, "cpw/mods/fml/common/discovery/asm/ASMModParser", "isBaseMod", "(Ljava/util/List;)Z", false);
                visitJumpInsn(Opcodes.IFEQ, continueLabel);
                visitVarInsn(Opcodes.ALOAD, classNameLocal);
                visitMethodInsn(Opcodes.INVOKESTATIC, "space/libs/util/forge/ModLoadingUtils", "find", "(Ljava/lang/String;)Z", false);
                visitJumpInsn(Opcodes.IFEQ, continueLabel);
                visitTypeInsn(Opcodes.NEW, "cpw/mods/fml/common/modloader/ModLoaderModContainer");
                visitInsn(Opcodes.DUP);
                visitVarInsn(Opcodes.ALOAD, classNameLocal);
                visitVarInsn(Opcodes.ALOAD, 2);
                visitVarInsn(Opcodes.ALOAD, 1);
                visitMethodInsn(Opcodes.INVOKEVIRTUAL, "cpw/mods/fml/common/discovery/asm/ASMModParser", "getBaseModProperties", "()Ljava/util/Properties;", false);
                visitMethodInsn(Opcodes.INVOKESPECIAL, "cpw/mods/fml/common/modloader/ModLoaderModContainer", "<init>", "(Ljava/lang/String;Ljava/io/File;Ljava/util/Properties;)V", false);
                visitInsn(Opcodes.ARETURN);
                visitLabel(continueLabel);
            }
        }
    }
}
