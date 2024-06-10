package space.libs.mixins.client;

import net.minecraft.client.settings.GameSettings;
import org.spongepowered.asm.mixin.*;
import space.libs.util.EnumHelperClientOption;
import space.libs.util.cursedmixinextensions.annotations.Public;

import static net.minecraft.client.settings.GameSettings.Options;

@SuppressWarnings("all")
@Mixin(value = Options.class, priority = 100)
public abstract class MixinOptions {

    @Public
    private static GameSettings.Options USE_SERVER_TEXTURES = EnumHelperClientOption.addEnum();

}
