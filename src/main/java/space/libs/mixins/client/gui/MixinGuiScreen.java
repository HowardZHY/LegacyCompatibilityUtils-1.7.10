package space.libs.mixins.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiScreen.class)
public abstract class MixinGuiScreen {

    @Shadow
    public int width;

    @Shadow
    public int height;

    public Minecraft field_73882_e;

    public int field_73880_f;

    public int field_73881_g;

    public FontRenderer field_73886_k;

    public void func_73872_a(Minecraft mc, int widthIn, int heightIn) {
        this.field_73882_e = mc;
        this.field_73886_k = mc.fontRendererObj;
        this.field_73880_f = widthIn;
        this.field_73881_g = heightIn;
    }

    @Inject(method = "setWorldAndResolution", at = @At("HEAD"))
    public void setWorldAndResolution(Minecraft mc, int widthIn, int heightIn, CallbackInfo ci) {
        this.func_73872_a(mc, widthIn, heightIn);
    }
}
