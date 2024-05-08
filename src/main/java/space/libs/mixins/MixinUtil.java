package space.libs.mixins;

import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import space.libs.util.cursedmixinextensions.annotations.Public;

import java.util.UUID;
import java.util.regex.Pattern;

@SuppressWarnings("all")
@Mixin(Util.class)
public class MixinUtil {
    @Public
    private static Pattern field_147174_a = Pattern.compile("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}");

    /** isUUIDStringDetermines */
    @Public
    private static boolean func_147172_a(String paramString) {
        return field_147174_a.matcher(paramString).matches();
    }

    /** tryGetUUIDFromString */
    @Public
    private static UUID func_147173_b(String paramString) {
        if (paramString == null)
            return null;
        if (func_147172_a(paramString))
            return UUID.fromString(paramString);
        if (paramString.length() == 32) {
            String str = paramString.substring(0, 8) + "-" + paramString.substring(8, 12) + "-" + paramString.substring(12, 16) + "-" + paramString.substring(16, 20) + "-" + paramString.substring(20, 32);
            if (func_147172_a(str))
                return UUID.fromString(str);
        }
        return null;
    }
}
