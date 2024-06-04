package space.libs.mixins.client;

import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.util.cursedmixinextensions.annotations.NewConstructor;
import space.libs.util.cursedmixinextensions.annotations.ShadowSuperConstructor;

@SuppressWarnings("unused")
@Mixin(ThreadDownloadImageData.class)
public abstract class MixinThreadDownloadImageData extends SimpleTexture {

    @Final
    @Mutable
    @Shadow
    private String imageUrl;

    @Final
    @Mutable
    @Shadow
    private IImageBuffer imageBuffer;

    @Shadow
    private boolean textureUploaded;

    @Shadow
    private void checkTextureUploaded() {}

    public MixinThreadDownloadImageData(ResourceLocation p_i1275_1_) {
        super(p_i1275_1_);
    }

    @ShadowSuperConstructor
    public void SimpleTexture(ResourceLocation p_i1275_1_) {}

    @NewConstructor
    public void ThreadDownloadImageData(String url, ResourceLocation location, IImageBuffer buffer) {
        SimpleTexture(location);
        this.imageUrl = url;
        this.imageBuffer = buffer;
    }

    /** isTextureUploaded */
    public boolean func_110557_a() {
        checkTextureUploaded();
        return this.textureUploaded;
    }

}
