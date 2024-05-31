package net.minecraft.client.gui;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.IChatComponent;

@SuppressWarnings("all")
@SideOnly(Side.CLIENT)
public class GuiScreenDisconnectedOnline extends GuiScreen {

    public String field_146867_a;

    public IChatComponent field_146865_f;

    public List<String> field_146866_g;

    public GuiScreen field_146868_h;

    public GuiScreenDisconnectedOnline(GuiScreen screen, String name, IChatComponent chatComponent) {
        this.field_146868_h = screen;
        this.field_146867_a = I18n.format(name, new Object[0]);
        this.field_146865_f = chatComponent;
    }

    protected void keyTyped(char c, int i) {}

    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, I18n.format("gui.back", new Object[0])));
        this.field_146866_g = this.fontRendererObj.listFormattedStringToWidth(this.field_146865_f.getFormattedText(), this.width - 50);
    }

    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) {
            this.mc.displayGuiScreen(this.field_146868_h);
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawCenteredString(this.fontRendererObj, this.field_146867_a, this.width / 2, this.height / 2 - 50, 11184810);
        int i = this.height / 2 - 30;
        if (this.field_146866_g != null) {
            for (String str : this.field_146866_g) {
                drawCenteredString(this.fontRendererObj, str, this.width / 2, i, 16777215);
                i += this.fontRendererObj.FONT_HEIGHT;
            }
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
