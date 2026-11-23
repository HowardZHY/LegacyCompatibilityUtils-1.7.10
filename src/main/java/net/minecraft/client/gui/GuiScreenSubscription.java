package net.minecraft.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import net.minecraft.client.Minecraft;
import net.minecraft.client.mco.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT) @SuppressWarnings({"unchecked", "unused"})
public class GuiScreenSubscription extends GuiScreen {

    private static final Logger logger = LogManager.getLogger();

    private final GuiScreen field_146780_f;

    private final McoServer field_146781_g;

    private final int field_146787_h = 0;

    private final int field_146788_i = 1;

    private int field_146785_r;

    private String field_146784_s;

    private final String field_146783_t = "https://account.mojang.com";

    private final String field_146782_u = "/buy/realms";

    public GuiScreenSubscription(GuiScreen screen, McoServer mcoServer) {
        this.field_146780_f = screen;
        this.field_146781_g = mcoServer;
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
    }

    @Override
    public void initGui() {
        this.func_146778_a(this.field_146781_g.field_148812_a);
        Keyboard.enableRepeatEvents(true);
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96 + 12, I18n.format("mco.configure.world.subscription.extend")));
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, I18n.format("gui.cancel")));
    }

    private void func_146778_a(long p_146778_1_) {
        Session session = this.mc.getSession();
        McoClient mcoclient = new McoClient(session.getSessionID(), session.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
        try {
            ValueObjectSubscription valueobjectsubscription = mcoclient.func_148705_g(p_146778_1_);
            this.field_146785_r = valueobjectsubscription.field_148789_b;
            this.field_146784_s = this.func_146776_b(valueobjectsubscription.field_148790_a);
        } catch (ExceptionMcoService exceptionmcoservice) {
            logger.error("Couldn't get subscription");
        } catch (IOException ioexception) {
            logger.error("Couldn't parse response subscribing");
        }
    }

    private String func_146776_b(long p_146776_1_) {
        GregorianCalendar gregoriancalendar = new GregorianCalendar(TimeZone.getDefault());
        gregoriancalendar.setTimeInMillis(p_146776_1_);
        return SimpleDateFormat.getDateTimeInstance().format(gregoriancalendar.getTime());
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    protected void actionPerformed(GuiButton p_146284_1_) {
        if (p_146284_1_.enabled) {
            if (p_146284_1_.id == 0) {
                this.mc.displayGuiScreen(this.field_146780_f);
            } else if (p_146284_1_.id == 1) {
                String s = "https://account.mojang.com/buy/realms?wid=" + this.field_146781_g.field_148812_a + "?pid=" + this.func_146777_g();
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(new StringSelection(s), null);
                this.func_146779_a(s);
            }
        }
    }

    private String func_146777_g() {
        String s = this.mc.getSession().getSessionID();
        String[] astring = s.split(":");
        return astring.length == 3 ? astring[2] : "";
    }

    private void func_146779_a(String p_146779_1_) {
        try {
            URI uri = new URI(p_146779_1_);
            Class<?> oclass = Class.forName("java.awt.Desktop");
            Object object = oclass.getMethod("getDesktop").invoke(null);
            oclass.getMethod("browse", URI.class).invoke(object, uri);
        } catch (Throwable throwable) {
            logger.error("Couldn't open link");
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
        this.drawCenteredString(this.fontRendererObj, I18n.format("mco.configure.world.subscription.title"), this.width / 2, 17, 16777215);
        this.drawString(this.fontRendererObj, I18n.format("mco.configure.world.subscription.start"), this.width / 2 - 100, 53, 10526880);
        this.drawString(this.fontRendererObj, this.field_146784_s, this.width / 2 - 100, 66, 16777215);
        this.drawString(this.fontRendererObj, I18n.format("mco.configure.world.subscription.daysleft"), this.width / 2 - 100, 85, 10526880);
        this.drawString(this.fontRendererObj, String.valueOf(this.field_146785_r), this.width / 2 - 100, 98, 16777215);
        super.drawScreen(par1, par2, par3);
    }
}
