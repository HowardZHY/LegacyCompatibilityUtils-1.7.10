package space.libs.mixins.client;

import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GLContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.libs.util.cursedmixinextensions.annotations.Public;

@SuppressWarnings("all")
@Mixin(OpenGlHelper.class)
public class MixinOpenGlHelper {

    @Public
    private static boolean field_77477_c;

    @Inject(method = "<clinit>", at = @At("RETURN"))
    private static void OpenGlHelper(CallbackInfo ci) {
        field_77477_c = GLContext.getCapabilities().GL_ARB_multitexture && !GLContext.getCapabilities().OpenGL13;
    }

}
