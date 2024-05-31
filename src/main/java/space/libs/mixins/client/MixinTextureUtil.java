package space.libs.mixins.client;

import net.minecraft.client.renderer.texture.TextureUtil;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.util.cursedmixinextensions.annotations.Public;

@SuppressWarnings("unused")
@Mixin(TextureUtil.class)
public class MixinTextureUtil {

    @Shadow
    private static int field_147958_e;

    @Shadow
    private static int field_147956_f;

    @Shadow
    private static void func_147954_b(boolean p_147954_0_, boolean p_147954_1_) {}

    @Public
    private static void func_147950_a(boolean b1, boolean b2) {
        field_147958_e = GL11.glGetTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER);
        field_147956_f = GL11.glGetTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER);
        func_147954_b(b1, b2);
    }

}
