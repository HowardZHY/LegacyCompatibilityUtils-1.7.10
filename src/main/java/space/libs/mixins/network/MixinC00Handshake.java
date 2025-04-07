package space.libs.mixins.network;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.handshake.client.C00Handshake;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.libs.interfaces.IHandshake;

@SuppressWarnings("unused")
@Mixin(C00Handshake.class)
public abstract class MixinC00Handshake implements IHandshake {

    @Shadow
    private String field_149598_b;

    private boolean hasFMLMarker = false;

    public boolean hasFMLMarker() {
        return this.hasFMLMarker;
    }

    @Inject(method = "readPacketData", at = @At("RETURN"))
    private void readPacketData(PacketBuffer data, CallbackInfo ci) {
        this.hasFMLMarker = this.field_149598_b.contains("\0FML\0");
    }
}
