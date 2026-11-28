package space.libs.mixins.interfaces;

import net.minecraft.world.chunk.IChunkProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(IChunkProvider.class)
public interface MixinIChunkProvider {

    /**
     * @author HowardZHY
     * @reason default
     */
    @SuppressWarnings("OverwriteModifiers")
    @Overwrite
    default void saveExtraData() {}

}
