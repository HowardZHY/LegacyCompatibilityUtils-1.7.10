package space.libs.mixins.network;

import net.minecraft.network.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(NetworkManager.class)
public abstract class MixinNetworkManager implements INetworkManager {

    @Shadow
    @Override
    public INetHandler getNetHandler() {
        throw new AbstractMethodError();
    }

}
