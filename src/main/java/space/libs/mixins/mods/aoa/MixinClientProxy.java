package space.libs.mixins.mods.aoa;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@SuppressWarnings("all")
@Pseudo
@Mixin(targets = "net.EternIsles.common.ClientProxy")
public class MixinClientProxy {

    /** Fix Eternal Isles & AoA2 Crash */
    @ModifyConstant(method = "RenderInformation", constant = @Constant(stringValue = "Statue"), remap = false)
    public String TileEntityStatue(String old) {
        return "EIStatue";
    }

    @ModifyConstant(method = "RenderInformation", constant = @Constant(stringValue = "Banner"), remap = false)
    public String TileEntityBanner(String old) {
        return "EIBanner";
    }

}

