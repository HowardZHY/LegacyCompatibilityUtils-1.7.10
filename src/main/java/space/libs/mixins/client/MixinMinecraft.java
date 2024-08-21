package space.libs.mixins.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ScreenShotHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.util.cursedmixinextensions.annotations.Public;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;

@SuppressWarnings("all")
@Mixin(Minecraft.class)
public abstract class MixinMinecraft {

    @Shadow
    public int displayHeight;

    @Shadow
    public int displayWidth;

    @Shadow
    private Framebuffer framebufferMc;

    @Shadow
    public GameSettings gameSettings;

    @Shadow
    public GuiIngame ingameGUI;

    @Final
    @Shadow
    public File mcDataDir;

    public boolean field_71414_F;

    @Shadow
    public void displayGuiScreen(GuiScreen guiScreenIn) {}

    /** screenshotListenerchecks */
    public void func_71365_K() {
        if (this.gameSettings.keyBindScreenshot.isPressed()) {
            if (!this.field_71414_F) {
                this.field_71414_F = true;
                this.ingameGUI.getChatGUI().printChatMessage(ScreenShotHelper.saveScreenshot(this.mcDataDir, this.displayWidth, this.displayHeight, this.framebufferMc));
            }
        } else {
            this.field_71414_F = false;
        }
    }

    public void func_71373_a(GuiScreen screen) {
        this.displayGuiScreen(screen);
    }

    /** readImage */
    public ByteBuffer func_110439_b(File par1File) throws IOException {
        BufferedImage bufferedimage = ImageIO.read(par1File);
        int[] aint = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), (int[])null, 0, bufferedimage.getWidth());
        ByteBuffer bytebuffer = ByteBuffer.allocate(4 * aint.length);
        int[] aint1 = aint;
        int i = aint.length;
        for (int j = 0; j < i; j++) {
            int k = aint1[j];
            bytebuffer.putInt(k << 8 | k >> 24 & 0xFF);
        }
        bytebuffer.flip();
        return bytebuffer;
    }

    @Public
    private static void func_147105_a(String p_147105_0_) {
        try {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Class<?> oclass = toolkit.getClass();
            if (oclass.getName().equals("sun.awt.X11.XToolkit")) {
                Field field = oclass.getDeclaredField("awtAppClassName");
                field.setAccessible(true);
                field.set(toolkit, p_147105_0_);
            }
        } catch (Exception exception) {}
    }

}
