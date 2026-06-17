package space.libs.mixins.network;

import net.minecraft.network.NetServerHandler;
import net.minecraft.server.network.NetHandlerLoginServer;
import org.spongepowered.asm.mixin.Mixin;
import space.libs.util.cursedmixinextensions.annotations.ChangeSuperClass;

@Mixin(NetHandlerLoginServer.class)
@ChangeSuperClass(NetServerHandler.class)
public abstract class MixinNetHandlerLoginServer {

}
