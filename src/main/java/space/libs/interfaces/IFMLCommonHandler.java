package space.libs.interfaces;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.world.World;

public interface IFMLCommonHandler {

    void rescheduleTicks(Side side);

    void onWorldLoadTick(World[] worlds);

}
