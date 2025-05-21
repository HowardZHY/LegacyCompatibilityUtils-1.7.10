package space.libs.interfaces;

import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

import java.util.EnumSet;

public interface IFMLCommonHandler {

    TickEvent.Type WORLDLOAD = addEnum();

    void rescheduleTicks(Side side);

    void tickStart(EnumSet<TickEvent.Type> ticks, Side side, Object ... data);

    void tickEnd(EnumSet<TickEvent.Type> ticks, Side side, Object ... data);

    void onWorldLoadTick(World[] worlds);

    static TickEvent.Type addEnum() {
        return EnumHelper.addEnum(TickEvent.Type.class, "WORLDLOAD", new Class[0], new Object[0]);
    }
}
