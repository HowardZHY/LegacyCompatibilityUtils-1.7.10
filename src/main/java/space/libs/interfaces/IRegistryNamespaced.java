package space.libs.interfaces;

import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import org.apache.logging.log4j.LogManager;

public interface IRegistryNamespaced {

    String getNameForObject(Object object);

    void compatlib$removeObject(String key, Object value);

    void compatlib$putObject(String key, Object value);

    static void renameBlock(String newName, Block object) {
        try {
            IRegistryNamespaced This = (IRegistryNamespaced) GameData.getBlockRegistry();
            String key = This.getNameForObject(object);
            String namespace = key.substring(0, key.lastIndexOf(':') + 1);
            This.compatlib$removeObject(key, object);
            This.compatlib$putObject(namespace.concat(newName), object);
            LogManager.getLogger().info("Renaming Legacy Block registry name from ".concat(key).concat(" to ".concat(newName)));
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't rename registry name for Legacy Block ".concat(newName));
        }
    }
}
