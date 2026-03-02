package space.libs.mixins.mods.aoa;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@SuppressWarnings("all")
@Pseudo
@Mixin(targets = "net.nevermine.dimension.gardencia.ChunkProviderGardencia", remap = false)
public abstract class MixinChunkProviderGardencia {

    /** Fix L'Borean Portal Structure WorldGen */
    @Dynamic
    @ModifyConstant(method = "func_73153_a", constant = @Constant(intValue = 63))
    public int populate(int old) {
        return 66;
    }
}
