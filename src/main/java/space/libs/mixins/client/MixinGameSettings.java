package space.libs.mixins.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(GameSettings.class)
public class MixinGameSettings {

    /** serverTextures */
    public boolean field_74356_s;

    @Inject(method = "<init>()V", at = @At("RETURN"))
    public void GameSettings(CallbackInfo ci) {
        this.field_74356_s = true;
    }

    @Inject(method = "<init>(Lnet/minecraft/client/Minecraft;Ljava/io/File;)V", at = @At("RETURN"))
    public void GameSettings(Minecraft p_i1016_1_, File p_i1016_2_, CallbackInfo ci) {
        this.field_74356_s = true;
    }

}
