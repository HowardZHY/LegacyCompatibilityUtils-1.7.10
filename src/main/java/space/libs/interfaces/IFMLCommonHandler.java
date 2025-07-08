package space.libs.interfaces;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet131MapData;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

import java.util.EnumSet;

public interface IFMLCommonHandler {

    TickEvent.Type WORLDLOAD = addEnum();

    static IFMLCommonHandler instance() {
        return (IFMLCommonHandler) FMLCommonHandler.instance();
    }

    void rescheduleTicks(Side side);

    void tickStart(EnumSet<TickEvent.Type> ticks, Side side, Object ... data);

    void tickEnd(EnumSet<TickEvent.Type> ticks, Side side, Object ... data);

    void onWorldLoadTick(World[] worlds);

    void handleTinyPacket(NetHandler handler, Packet131MapData mapData);

    static TickEvent.Type addEnum() {
        return EnumHelper.addEnum(TickEvent.Type.class, "WORLDLOAD", new Class[0], new Object[0]);
    }
}
