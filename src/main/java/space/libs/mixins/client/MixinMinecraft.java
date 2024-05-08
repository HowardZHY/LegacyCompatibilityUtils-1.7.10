package space.libs.mixins.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ScreenShotHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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

}
