package space.libs.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;
import space.libs.asm.visitors.*;

@SuppressWarnings("unused")
public class ClassTransformers implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (name == null || bytes == null) {
            return bytes;
        }
        if (name.startsWith("cp")) {
            if (name.equals("cpw.mods.fml.common.FMLModContainer")) {
                return TransformerUtils.transformSafe(bytes, ClassWriter.COMPUTE_MAXS, FMLModContainerVisitor.class, 0);
            } else {
                if (name.equals("cpw.mods.fml.common.ModContainerFactory")) {
                    return TransformerUtils.transformSafe(bytes, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES,
                        ModContainerFactoryVisitor.class, ClassReader.EXPAND_FRAMES);
                }
            }
        } else {
            if (name.startsWith("R") && "Reika.DragonAPI.Instantiable.Data.Maps.MultiMap".equals(name)) {
                return TransformerUtils.transformSafe(bytes, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES, DragonAPIVisitor.class, ClassReader.EXPAND_FRAMES);
            }
        }
        if (ClassNameList.ShouldNotTransform(name)) {
            return bytes;
        } else {
            return TransformerUtils.transformSafe(bytes, 0, EventVisitor.class, 0);
        }
    }

}
