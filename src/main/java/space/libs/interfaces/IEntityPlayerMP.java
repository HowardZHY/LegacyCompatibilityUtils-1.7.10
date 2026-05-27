package space.libs.interfaces;

import net.minecraft.network.NetServerHandler;
import space.libs.fml.network.Player;

public interface IEntityPlayerMP extends Player {

    NetServerHandler getNetServerHandler();

}
