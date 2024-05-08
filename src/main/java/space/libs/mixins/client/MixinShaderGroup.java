package space.libs.mixins.client;

import com.google.gson.JsonElement;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;

@SuppressWarnings("unused")
@Mixin(ShaderGroup.class)
public class MixinShaderGroup {

    /** initPass*/
    private void func_148019_b(JsonElement paramJsonElement) {
        throw new UnsupportedOperationException();
    }

    /** initFromLocation */
    public void func_148025_a(ResourceLocation location) {
        throw new UnsupportedOperationException();
    }

}
