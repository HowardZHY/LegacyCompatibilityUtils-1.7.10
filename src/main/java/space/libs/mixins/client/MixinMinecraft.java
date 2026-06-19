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
import space.libs.util.MappedName;
import space.libs.util.cursedmixinextensions.annotations.Public;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;

@SuppressWarnings("unused")
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

    @MappedName("screenshotListenerChecks")
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

    @MappedName("readImage")
    public ByteBuffer func_110439_b(File file) throws IOException {
        BufferedImage bufferedimage = ImageIO.read(file);
        int[] array = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), null, 0, bufferedimage.getWidth());
        ByteBuffer bytebuffer = ByteBuffer.allocate(4 * array.length);
        int i = array.length;
        for (int k : array) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
