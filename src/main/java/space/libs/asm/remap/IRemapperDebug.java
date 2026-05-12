package space.libs.asm.remap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import space.libs.core.CompatLibDebug;

public interface IRemapperDebug {

    Logger LOGGER = LogManager.getLogger();

    boolean DEBUG_REMAPPING = CompatLibDebug.DEBUG_REMAP;

    boolean DEBUG_CUSTOM_REMAPPING = CompatLibDebug.DEBUG_CUSTOM_REMAP;

    default void DebugRemap(String msg) {
        if (this instanceof CustomRemapper) {
            LOGGER.info(msg);
        }
    }
}
