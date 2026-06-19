package space.libs.mixins.network;

import net.minecraft.network.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.net.SocketAddress;

@Mixin(NetworkManager.class)
public abstract class MixinNetworkManager implements INetworkManager {

    @Shadow
    @Override
    public INetHandler getNetHandler() {
        throw new AbstractMethodError();
    }

    @Shadow(aliases = "getRemoteAddress")
    @Override
    public SocketAddress func_74430_c() {
        throw new AbstractMethodError();
    }

}
