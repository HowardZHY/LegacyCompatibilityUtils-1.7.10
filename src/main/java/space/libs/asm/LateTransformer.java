package space.libs.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
import space.libs.asm.visitors.CheckCastVisitor;
import space.libs.core.ICoreUtils;

@SuppressWarnings("unused")
public class LateTransformer implements IClassTransformer {

    private static final String BlockBlastingCap = "com.moreexplosives.block.BlockBlastingCap";

    private static final String BlockLiquid = "net.minecraft.block.BlockLiquid";

    private static final String BlockFluidType = "net/minecraft/block/BlockFluid";

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (name == null || bytes == null) {
            return bytes;
        }
        if (BlockBlastingCap.equals(name)){
            return TransformerUtils.transformSafe(bytes, ClassWriter.COMPUTE_MAXS, CheckCastVisitor.class, ClassReader.EXPAND_FRAMES);
        }
        if ("alw".equals(name) || BlockLiquid.equals(name)) {
            return transformBlockLiquid(bytes);
        }
        return bytes;
    }

    private static byte[] transformBlockLiquid(byte[] bytes) {
        ClassNode classNode = new ClassNode(Opcodes.ASM5);
        ClassReader reader = new ClassReader(bytes);
        reader.accept(classNode, ClassReader.EXPAND_FRAMES);
        ICoreUtils.LOGGER.info("Transforming BlockLiquid SuperClass from ".concat(classNode.superName));
        classNode.superName = BlockFluidType;
        classNode.signature = null;
        fixConstructors(classNode);
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classNode.accept(writer);
        return writer.toByteArray();
    }

    private static void fixConstructors(ClassNode classNode) {
        for (MethodNode method : classNode.methods) {
            if (!"<init>".equals(method.name)) {
                continue;
            }
            for (AbstractInsnNode insn = method.instructions.getFirst(); insn != null; insn = insn.getNext()) {
                if (!(insn instanceof MethodInsnNode)) {
                    continue;
                }
                MethodInsnNode call = (MethodInsnNode) insn;
                if (call.getOpcode() == Opcodes.INVOKESPECIAL && "<init>".equals(call.name) && "net/minecraft/block/Block".equals(call.owner)) {
                    call.owner = BlockFluidType;
                }
            }
        }
    }
}
