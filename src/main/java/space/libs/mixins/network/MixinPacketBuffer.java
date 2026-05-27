package space.libs.mixins.network;

import net.minecraft.network.PacketBuffer;
import org.spongepowered.asm.mixin.Mixin;
import space.libs.interfaces.IPacketBuffer;

@Mixin(PacketBuffer.class)
public class MixinPacketBuffer implements IPacketBuffer {

}
