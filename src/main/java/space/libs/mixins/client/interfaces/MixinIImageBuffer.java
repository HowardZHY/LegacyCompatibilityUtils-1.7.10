package space.libs.mixins.client.interfaces;

import net.minecraft.client.renderer.IImageBuffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(IImageBuffer.class)
public interface MixinIImageBuffer {

    /**
     * @author HowardZHY
     * @reason default
     */
    @SuppressWarnings("all")
    @Overwrite
    default void func_152634_a() {}

}
