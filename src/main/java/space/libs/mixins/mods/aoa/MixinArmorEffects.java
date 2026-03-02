package space.libs.mixins.mods.aoa;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@SuppressWarnings("all")
@Pseudo
@Mixin(targets = "net.EternIsles.assist.armorEffects", remap = false)
public abstract class MixinArmorEffects {

    /** Fix Eternal Isles Crash */
    @ModifyConstant(method = "<clinit>", constant = @Constant(stringValue = "ag"))
    private static String isImmuneToFire(String old) {
        return "ae";
    }

    @ModifyConstant(method = "<clinit>", constant = @Constant(stringValue = "bd"))
    private static String isJumping(String old) {
        return "bc";
    }

}
