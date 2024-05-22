package space.libs.mixins.mods.ichun;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
@Mixin(targets = "ichun.common.core.updateChecker.ModVersionChecker", remap = false)
public class MixinModVersionChecker {
    /**
     * @author HowardZHY
     * @reason Optimize
     */
    @Overwrite
    public static void init() {}
}
