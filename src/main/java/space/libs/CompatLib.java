package space.libs;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("all")
@Mod(
    modid = "compatlib",
    version = "1.7.10",
    name = "CompatLib",
    acceptedMinecraftVersions = "*",
    acceptableRemoteVersions = "*"
)
public class CompatLib {

    public static final String MODID = "compatlib";

    public static final String MODNAME = "CompatLib";

    public static final String VERSION = "1.7.10";

    public static final Logger LOGGER = LogManager.getLogger("CompatLib");

    public CompatLib() {
        LOGGER.info("[CompatLib] Loading...");
        if (FMLCommonHandler.instance().getSide().isClient()) {
            this.onClientModLoading();
        }
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {}

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        //FMLCommonHandler.instance().bus().register(new CompatTickHandler());
    }

    public void onClientModLoading() {}

}
