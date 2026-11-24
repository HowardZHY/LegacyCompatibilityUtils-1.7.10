package net.minecraft.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.net.URI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.mco.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT) @SuppressWarnings("all")
public class GuiScreenReamlsTOS extends GuiScreen {

    public static final Logger logger = LogManager.getLogger();

    public final GuiScreen field_146770_f;

    public final McoServer field_146771_g;

    public GuiButton field_146774_h;

    public boolean field_146775_i = false;

    public String field_146772_r = "https://minecraft.net/realms/terms";

    public GuiScreenReamlsTOS(GuiScreen p_i45045_1_, McoServer p_i45045_2_) {
        this.field_146770_f = p_i45045_1_;
        this.field_146771_g = p_i45045_2_;
    }

    @Override
    public void updateScreen() {}

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        int i = this.width / 4;
        int j = this.width / 4 - 2;
        int k = this.width / 2 + 4;
        this.buttonList.add(this.field_146774_h = new GuiButton(1, i, this.height / 5 + 96 + 22, j, 20, I18n.format("mco.terms.buttons.agree")));
        this.buttonList.add(new GuiButton(2, k, this.height / 5 + 96 + 22, j, 20, I18n.format("mco.terms.buttons.disagree")));
    }

    @Override
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.enabled) {
            if (button.id == 2) {
                this.mc.displayGuiScreen(this.field_146770_f);
            } else if (button.id == 1) {
                this.func_146768_g();
            }
        }
    }

    public void func_146768_g() {
        Session session = this.mc.getSession();
        McoClient mcoclient = new McoClient(session.getSessionID(), session.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
        try {
            mcoclient.func_148714_h();
            GuiScreenLongRunningTask guiscreenlongrunningtask = new GuiScreenLongRunningTask(this.mc, this, new TaskOnlineConnect(this, this.field_146771_g));
            guiscreenlongrunningtask.func_146902_g();
            this.mc.displayGuiScreen(guiscreenlongrunningtask);
        } catch (ExceptionMcoService exceptionmcoservice) {
            logger.error("Couldn't agree to TOS");
        }
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) {
        super.mouseClicked(par1, par2, par3);
        if (this.field_146775_i) {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(new StringSelection(this.field_146772_r), null);
            this.func_146769_a(this.field_146772_r);
        }
    }

    public void func_146769_a(String link) {
        try {
            URI uri = new URI(link);
            Class<?> oclass = Class.forName("java.awt.Desktop");
            Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null);
            oclass.getMethod("browse", new Class[] {URI.class}).invoke(object, uri);
        } catch (Throwable throwable) {
            logger.error("Couldn't open link");
        }
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("mco.terms.title"), this.width / 2, 17, 16777215);
        this.drawString(this.fontRendererObj, I18n.format("mco.terms.sentence.1"), this.width / 2 - 120, 87, 16777215);
        int k = this.fontRendererObj.getStringWidth(I18n.format("mco.terms.sentence.1"));
        int l = 3368635;
        int i1 = 7107012;
        int j1 = this.width / 2 - 121 + k;
        byte b0 = 86;
        int k1 = j1 + this.fontRendererObj.getStringWidth("mco.terms.sentence.2") + 1;
        int l1 = 87 + this.fontRendererObj.FONT_HEIGHT;
        if (j1 <= par1 && par1 <= k1 && b0 <= par2 && par2 <= l1) {
            this.field_146775_i = true;
            this.drawString(this.fontRendererObj, " " + I18n.format("mco.terms.sentence.2"), this.width / 2 - 120 + k, 87, i1);
        } else {
            this.field_146775_i = false;
            this.drawString(this.fontRendererObj, " " + I18n.format("mco.terms.sentence.2"), this.width / 2 - 120 + k, 87, l);
        }
        super.drawScreen(par1, par2, par3);
    }
}
