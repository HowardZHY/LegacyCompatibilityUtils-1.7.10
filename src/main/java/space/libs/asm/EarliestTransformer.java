package space.libs.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;
import space.libs.asm.visitors.ConfigurationVisitor;

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

}
