package space.libs.mixins.interfaces;

import com.google.gson.JsonElement;
import net.minecraft.util.IJsonSerializable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(IJsonSerializable.class)
public interface MixinIJsonSerializable {

    /**
     * @author HowardZHY
     * @reason default
     */
    @SuppressWarnings("OverwriteModifiers")
    @Overwrite
    default void func_152753_a(JsonElement jsonElement) {}
}
