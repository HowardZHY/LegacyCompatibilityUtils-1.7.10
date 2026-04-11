package space.libs.core;

import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.apache.logging.log4j.*;

public interface ICoreUtils {

    Logger LOGGER = LogManager.getLogger("CompatLibCore");

    LaunchClassLoader classLoader = Launch.classLoader;

}
