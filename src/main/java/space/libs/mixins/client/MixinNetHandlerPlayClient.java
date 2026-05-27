package space.libs.mixins.client;

import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.client.network.NetHandlerPlayClient;
import org.spongepowered.asm.mixin.Mixin;
import space.libs.util.cursedmixinextensions.annotations.ChangeSuperClass;

@Mixin(NetHandlerPlayClient.class)
@ChangeSuperClass(NetClientHandler.class)
public abstract class MixinNetHandlerPlayClient {

}
