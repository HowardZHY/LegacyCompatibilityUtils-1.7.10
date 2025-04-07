package space.libs.mixins.network;

import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NetworkManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@SuppressWarnings("unused")
@Mixin(NetworkManager.class)
public abstract class MixinNetworkManager {

    @Shadow
    private @Final boolean isClientSide;

    public EnumPacketDirection getDirection() {
        if (this.isClientSide) {
            return EnumPacketDirection.CLIENTBOUND;
        } else {
            return EnumPacketDirection.SERVERBOUND;
        }
    }

}
