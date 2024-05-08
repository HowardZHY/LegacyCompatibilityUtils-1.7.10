package space.libs.mixins.client;

import net.minecraft.client.renderer.ThreadDownloadImageData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@SuppressWarnings("unused")
@Mixin(ThreadDownloadImageData.class)
public class MixinThreadDownloadImageData {

    @Shadow
    private boolean textureUploaded;

    @Shadow
    private void checkTextureUploaded() {}

    /** isTextureUploaded */
    public boolean func_110557_a() {
        checkTextureUploaded();
        return this.textureUploaded;
    }

}
