package space.libs.mixins.mods.aoa;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Pseudo;

import java.io.IOException;
import java.net.MalformedURLException;

@SuppressWarnings("all")
@Pseudo
@Mixin(targets = "net.nevermine.assist.UpdateChecker", remap = false)
public class MixinUpdateChecker {
    /**
     * @author HowardZHY
     * @reason Optimize
     */
    @Overwrite
    public static boolean isUpdateAvailable() throws IOException, MalformedURLException {
        return false;
    }
}
