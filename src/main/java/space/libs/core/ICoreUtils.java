package space.libs.core;

import net.minecraft.launchwrapper.*;
import org.apache.logging.log4j.*;

import java.util.concurrent.*;

public interface ICoreUtils {

    Logger LOGGER = LogManager.getLogger("CompatLibCore");

    LaunchClassLoader classLoader = Launch.classLoader;

    ExecutorService EXECUTOR = Executors.newFixedThreadPool(8);

}
