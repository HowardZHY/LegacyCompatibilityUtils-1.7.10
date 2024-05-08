package space.libs.mixins.mods.aoa;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@SuppressWarnings("all")
@Pseudo
@Mixin(targets = "net.EternIsles.assist.armorEffects")
public class MixinArmorEffects {

    /** Fix Eternal Isles Crash */
    @ModifyConstant(method = "<clinit>", constant = @Constant(stringValue = "ag"), remap = false)
    private static String isImmuneToFire(String old) {
        return "ae";
    }

    @ModifyConstant(method = "<clinit>", constant = @Constant(stringValue = "bd"), remap = false)
    private static String isJumping(String old) {
        return "bc";
    }

}
