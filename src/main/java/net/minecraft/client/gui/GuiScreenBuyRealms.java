package net.minecraft.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.mco.ExceptionMcoService;
import net.minecraft.client.mco.McoClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT) @SuppressWarnings("all")
public class GuiScreenBuyRealms extends GuiScreen {

    private static final Logger logger = LogManager.getLogger();

    private GuiScreen field_146817_f;

    private static int field_146818_g = 111;

    private volatile String field_146820_h = "";

    public GuiScreenBuyRealms(GuiScreen p_i45035_1_) {
        this.field_146817_f = p_i45035_1_;
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        short short1 = 212;
        this.buttonList.add(new GuiButton(field_146818_g, this.width / 2 - short1 / 2, 180, short1, 20, I18n.format("gui.back")));
        this.func_146816_h();
    }

    private void func_146816_h() {
        Session session = this.mc.getSession();
        final McoClient mcoclient = new McoClient(session.getSessionID(), session.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
        (new Thread() {
            @Override
            public void run() {
                try {
                    GuiScreenBuyRealms.this.field_146820_h = mcoclient.func_148690_i();
                } catch (ExceptionMcoService exceptionmcoservice) {
                    GuiScreenBuyRealms.logger.error("Could not get stat");
                }
            }
        }).start();
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.enabled) {
            if (button.id == field_146818_g) {
                this.mc.displayGuiScreen(this.field_146817_f);
            }
        }
    }

    @Override
    protected void keyTyped(char par1, int par2) {}

    @Override
    protected void mouseClicked(int par1, int par2, int par3) {
        super.mouseClicked(par1, par2, par3);
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("mco.buy.realms.title"), this.width / 2, 11, 16777215);
        String[] astring = this.field_146820_h.split("\n");
        int k = 52;
        String[] astring1 = astring;
        int l = astring.length;
        for (int i1 = 0; i1 < l; ++i1) {
            String s = astring1[i1];
            this.drawCenteredString(this.fontRendererObj, s, this.width / 2, k, 10526880);
            k += 18;
        }
        super.drawScreen(par1, par2, par3);
    }
}
