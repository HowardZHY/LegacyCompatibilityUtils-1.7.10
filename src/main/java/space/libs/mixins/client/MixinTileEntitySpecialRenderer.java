package space.libs.mixins.client;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.libs.util.MappedName;

@Mixin(TileEntitySpecialRenderer.class)
public abstract class MixinTileEntitySpecialRenderer {

    @Shadow
    protected abstract void bindTexture(ResourceLocation resourceLocation);

    @Shadow
    public abstract void func_147497_a(TileEntityRendererDispatcher tileEntityRendererDispatcher);

    @Shadow
    public abstract void onWorldChange(World world);

    @Shadow
    public abstract FontRenderer func_147498_b();

    public boolean LegacyRender = false;

    /**
     * @author HowardZHY
     * @reason No Abstract
     */
    @SuppressWarnings("OverwriteModifiers")
    @Overwrite
    public void renderTileEntityAt(TileEntity te, double d, double e, double f, float g) {
        if (this.LegacyRender) {
            this.LegacyRender = false;
        } else {
            this.func_76894_a(te, d, e, f, g);
        }
    }

    @Inject(method = "func_147497_a", at = @At("HEAD"))
    public void func_147497_a(TileEntityRendererDispatcher dispatcher, CallbackInfo ci) {
        this.field_76898_b = dispatcher;
    }

    protected TileEntityRendererDispatcher field_76898_b;

    @MappedName(value = "renderTileEntityAt", until = "1.6.4")
    public void func_76894_a(TileEntity te, double d, double e, double f, float g) {
        this.LegacyRender = true;
        this.renderTileEntityAt(te, d, e, f, g);
    }

    protected void func_110628_a(ResourceLocation location) {
        this.bindTexture(location);
    }

    public void func_76893_a(TileEntityRendererDispatcher renderer) {
        this.func_147497_a(renderer);
    }

    public void func_76896_a(World world) {
        this.onWorldChange(world);
    }

    public FontRenderer func_76895_b() {
        return this.func_147498_b();
    }
}
