package space.libs.asm;

import java.util.Objects;

import net.minecraft.launchwrapper.IClassTransformer;

import org.objectweb.asm.*;
import static org.objectweb.asm.Opcodes.*;

public class GameDataTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (!Objects.equals(name, "cpw.mods.fml.common.registry.GameData")) {
            return bytes;
        }
        return bytes;
    }
}
