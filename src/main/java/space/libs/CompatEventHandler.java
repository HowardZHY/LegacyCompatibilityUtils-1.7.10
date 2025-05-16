package space.libs;

import cpw.mods.fml.common.FMLCommonHandler;
import space.libs.interfaces.IFMLCommonHandler;

public class CompatEventHandler {

    @SuppressWarnings("all")
    public static final IFMLCommonHandler HANDLER = (IFMLCommonHandler) FMLCommonHandler.instance();

    public CompatEventHandler() {}

}
