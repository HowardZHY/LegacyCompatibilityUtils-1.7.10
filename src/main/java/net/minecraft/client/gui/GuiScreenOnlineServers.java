package net.minecraft.client.gui;

import org.lwjgl.input.Keyboard;

@SuppressWarnings("all")
public class GuiScreenOnlineServers extends GuiScreen {

    public long field_146705_v;

    public GuiScreen field_146707_t;

    public GuiScreenOnlineServers(GuiScreen screen) {
        this.field_146707_t = screen;
    }

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.func_146688_g();
    }

    public void func_146688_g() {

    }

    public void updateScreen() {
        super.updateScreen();
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    protected void actionPerformed(GuiButton paramGuiButton) {

    }

    public void confirmClicked(boolean result, int id) {

    }

    public void func_146670_h() {
        this.field_146705_v = -1L;
    }

    protected void keyTyped(char c, int i) {
        super.keyTyped(c, i);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    protected void func_146658_b(String s, int i, int j) {

    }
}
