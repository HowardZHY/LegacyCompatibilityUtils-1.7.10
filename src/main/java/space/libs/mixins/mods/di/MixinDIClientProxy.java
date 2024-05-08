package space.libs.mixins.mods.di;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Pseudo;

@SuppressWarnings("all")
@Pseudo
@Mixin(targets = "DamageIndicatorsMod.client.DIClientProxy", remap = false)
public class MixinDIClientProxy {
    /**
     * @author HowardZHY
     * @reason Optimize
     */
    @Overwrite
    public void trysendmessage() {}
}
