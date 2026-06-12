package space.libs.mixins.client;

import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import space.libs.util.MappedName;
import space.libs.util.PlayerUtils;
import space.libs.util.cursedmixinextensions.annotations.Public;

import java.util.UUID;
import java.util.regex.Pattern;

@Mixin(Util.class)
public abstract class MixinUtil {

    @Public
    private static Pattern field_147174_a = PlayerUtils.UUID;

    @MappedName("isUUIDStringDetermines")
    @Public
    private static boolean func_147172_a(String s) {
        return field_147174_a.matcher(s).matches();
    }

    @MappedName("tryGetUUIDFromString")
    @Public
    private static UUID func_147173_b(String s) {
        if (s == null) {
            return null;
        }
        if (func_147172_a(s)) {
            return UUID.fromString(s);
        }
        if (s.length() == 32) {
            String s1 = s.substring(0, 8) + "-" + s.substring(8, 12) + "-" + s.substring(12, 16) + "-" + s.substring(16, 20) + "-" + s.substring(20, 32);
            if (func_147172_a(s1)) {
                return UUID.fromString(s1);
            }
        }
        return null;
    }
}
