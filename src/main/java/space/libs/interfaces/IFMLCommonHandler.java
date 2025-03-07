package space.libs.interfaces;

import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.world.World;

import java.util.EnumSet;

public interface IFMLCommonHandler {

    void rescheduleTicks(Side side);

    void tickStart(EnumSet<TickEvent.Type> ticks, Side side, Object ... data);

    void tickEnd(EnumSet<TickEvent.Type> ticks, Side side, Object ... data);

    void onWorldLoadTick(World[] worlds);

}
