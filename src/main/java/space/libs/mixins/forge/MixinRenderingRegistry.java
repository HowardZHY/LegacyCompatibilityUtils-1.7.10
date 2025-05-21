package space.libs.mixins.forge;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import space.libs.fml.client.TextureFXManager;
import space.libs.interfaces.IRenderingRegistry;
import space.libs.util.cursedmixinextensions.annotations.Public;

@SideOnly(Side.CLIENT)
@Mixin(value = RenderingRegistry.class, remap = false)
public class MixinRenderingRegistry implements IRenderingRegistry {

    @Public
    private static int addTextureOverride(String fileToOverride, String fileToAdd) {
        return -1;
    }

    @Public
    private static void addTextureOverride(String path, String overlayPath, int index) {
        TextureFXManager.instance().addNewTextureOverride(path, overlayPath, index);
    }

    public int addOverride(String fileToOverride, String fileToAdd) {
        return addTextureOverride(fileToOverride, fileToAdd);
    }

    public void addOverride(String path, String overlayPath, int index) {
        addTextureOverride(path, overlayPath, index);
    }
}
