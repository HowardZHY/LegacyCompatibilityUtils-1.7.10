package space.libs.mixins.network;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetworkManager;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(NetworkManager.class)
public abstract class MixinNetworkManager implements INetworkManager {

}
