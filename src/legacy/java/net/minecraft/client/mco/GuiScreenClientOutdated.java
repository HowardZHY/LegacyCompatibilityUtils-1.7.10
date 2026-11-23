package net.minecraft.client.mco;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

@SideOnly(Side.CLIENT)
public class GuiScreenClientOutdated extends GuiScreen {

    private final GuiScreen field_146901_a;

    public GuiScreenClientOutdated(GuiScreen p_i2323_1_) {
        this.field_146901_a = p_i2323_1_;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    @Override @SuppressWarnings("unchecked")
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, "Back"));
    }

    /**
     * Draws the screen and all the components in it.
     */
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        String s = I18n.format("mco.client.outdated.title");
        String s1 = I18n.format("mco.client.outdated.msg");
        this.drawCenteredString(this.fontRendererObj, s, this.width / 2, this.height / 2 - 50, 16711680);
        this.drawCenteredString(this.fontRendererObj, s1, this.width / 2, this.height / 2 - 30, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) {
            this.mc.displayGuiScreen(this.field_146901_a);
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == 28 || keyCode == 156) {
            this.mc.displayGuiScreen(this.field_146901_a);
        }
    }
}
