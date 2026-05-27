package space.libs.mixins.network;

import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.NetServerHandler;
import org.spongepowered.asm.mixin.Mixin;
import space.libs.util.cursedmixinextensions.annotations.ChangeSuperClass;

@Mixin(NetHandlerPlayServer.class)
@ChangeSuperClass(NetServerHandler.class)
public abstract class MixinNetHandlerPlayServer {

}
