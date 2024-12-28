package space.libs;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import space.libs.interfaces.IFMLCommonHandler;

import java.util.EnumSet;

public class CompatTickHandler {

    public static IFMLCommonHandler HANDLER = (IFMLCommonHandler) FMLCommonHandler.instance();

    public CompatTickHandler() {}

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            HANDLER.tickStart(EnumSet.of(TickEvent.Type.SERVER), Side.SERVER);;
        } else {
            HANDLER.tickEnd(EnumSet.of(TickEvent.Type.SERVER), Side.SERVER);;
        }
    }
}
