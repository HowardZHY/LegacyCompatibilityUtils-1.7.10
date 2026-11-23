package net.minecraft.client.gui.mco;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.net.URI;
import java.text.DateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.mco.*;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT) @SuppressWarnings({"unchecked", "unused"})
public class GuiScreenBackup extends GuiScreen {

    private static final AtomicInteger field_146845_a = new AtomicInteger(0);

    private static final Logger field_146841_f = LogManager.getLogger();

    private final GuiScreenConfigureWorld field_146842_g;

    private final long field_146846_h;

    private List<Backup> field_146847_i = Collections.emptyList();

    private GuiScreenBackup.SelectionList field_146844_r;

    private int field_146843_s = -1;

    public GuiScreenBackup(GuiScreenConfigureWorld p_i1103_1_, long p_i1103_2_) {
        this.field_146842_g = p_i1103_1_;
        this.field_146846_h = p_i1103_2_;
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.field_146844_r = new GuiScreenBackup.SelectionList();
        (new Thread("MCO Backup Requester #" + field_146845_a.incrementAndGet()) {
            @Override
            public void run() {
                Session session = GuiScreenBackup.this.mc.getSession();
                McoClient mcoclient = new McoClient(session.getSessionID(), session.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
                try {
                    GuiScreenBackup.this.field_146847_i = mcoclient.func_148704_d(GuiScreenBackup.this.field_146846_h).field_148797_a;
                } catch (ExceptionMcoService exceptionmcoservice) {
                    GuiScreenBackup.field_146841_f.error("Couldn't request backups", exceptionmcoservice);
                }
            }
        }).start();
        this.func_146840_h();
    }

    private void func_146840_h() {
        this.buttonList.add(new GuiButton(2, this.width / 2 + 6, this.height - 52, 153, 20, I18n.format("mco.backup.button.download")));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 154, this.height - 52, 153, 20, I18n.format("mco.backup.button.restore")));
        this.buttonList.add(new GuiButton(0, this.width / 2 - 74, this.height - 52 + 25, 153, 20, I18n.format("gui.back")));
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.enabled) {
            if (button.id == 1) {
                this.func_146827_i();
            } else if (button.id == 0) {
                this.mc.displayGuiScreen(this.field_146842_g);
            } else if (button.id == 2) {
                this.func_146826_p();
            } else {
                this.field_146844_r.func_148357_a(button);
            }
        }
    }

    private void func_146827_i()
    {
        if (this.field_146843_s >= 0 && this.field_146843_s < this.field_146847_i.size())
        {
            Date date = this.field_146847_i.get(this.field_146843_s).field_148778_b;
            String s = DateFormat.getDateTimeInstance(3, 3).format(date);
            String s1 = this.func_146829_a(System.currentTimeMillis() - date.getTime());
            String s2 = I18n.format("mco.configure.world.restore.question.line1") + " '" + s + "' (" + s1 + ")";
            String s3 = I18n.format("mco.configure.world.restore.question.line2");
            this.mc.displayGuiScreen(new GuiScreenConfirmation(this, GuiScreenConfirmation.ConfirmationType.Warning, s2, s3, 1));
        }
    }

    private void func_146826_p() {
        String s = I18n.format("mco.configure.world.restore.download.question.line1");
        String s1 = I18n.format("mco.configure.world.restore.download.question.line2");
        this.mc.displayGuiScreen(new GuiScreenConfirmation(this, GuiScreenConfirmation.ConfirmationType.Info, s, s1, 2));
    }

    private void func_146821_q() {
        Session session = this.mc.getSession();
        McoClient mcoclient = new McoClient(session.getSessionID(), session.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
        try {
            String s = mcoclient.func_148708_h(this.field_146846_h);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(new StringSelection(s), null);
            this.func_146823_a(s);
        }
        catch (ExceptionMcoService exceptionmcoservice)
        {
            field_146841_f.error("Couldn't download world data");
        }
    }

    private void func_146823_a(String p_146823_1_) {
        try {
            URI uri = new URI(p_146823_1_);
            Class<?> oclass = Class.forName("java.awt.Desktop");
            Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null);
            oclass.getMethod("browse", new Class[] {URI.class}).invoke(object, uri);
        }
        catch (Throwable throwable)
        {
            field_146841_f.error("Couldn't open link");
        }
    }

    public void func_73878_a(boolean p_73878_1_, int p_73878_2_) {
        if (p_73878_1_ && p_73878_2_ == 1) {
            this.func_146839_r();
        } else if (p_73878_1_ && p_73878_2_ == 2) {
            this.func_146821_q();
        } else {
            this.mc.displayGuiScreen(this);
        }
    }

    private void func_146839_r() {
        Backup backup = this.field_146847_i.get(this.field_146843_s);
        GuiScreenBackup.RestoreTask restoretask = new GuiScreenBackup.RestoreTask(backup, null);
        GuiScreenLongRunningTask guiscreenlongrunningtask = new GuiScreenLongRunningTask(this.mc, this.field_146842_g, restoretask);
        guiscreenlongrunningtask.func_146902_g();
        this.mc.displayGuiScreen(guiscreenlongrunningtask);
    }

    @Override
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
        this.drawDefaultBackground();
        this.field_146844_r.func_148350_a(p_73863_1_, p_73863_2_, p_73863_3_);
        this.drawString(this.fontRendererObj, I18n.format("mco.backup.title"), this.width / 2, 20, 16777215);
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }

    private String func_146829_a(Long p_146829_1_) {
        if (p_146829_1_ < 0L)
            return "right now";
        long i = p_146829_1_ / 1000L;
        if (i < 60L)
            return ((i == 1L) ? "1 second" : (i + " seconds")) + " ago";
        if (i < 3600L) {
            long l = i / 60L;
            return ((l == 1L) ? "1 minute" : (l + " minutes")) + " ago";
        }
        if (i < 86400L) {
            long l = i / 3600L;
            return ((l == 1L) ? "1 hour" : (l + " hours")) + " ago";
        }
        long j = i / 86400L;
        return ((j == 1L) ? "1 day" : (j + " days")) + " ago";
    }

    @SideOnly(Side.CLIENT)
    public class RestoreTask extends TaskLongRunning {

        private final Backup field_148424_c;

        public RestoreTask(Backup p_i1101_2_) {
            this.field_148424_c = p_i1101_2_;
        }

        @Override
        public void run() {
            this.func_148417_b(I18n.format("mco.backup.restoring"));
            try {
                if (this.func_148418_c()) {
                    return;
                }
                Session session = GuiScreenBackup.this.mc.getSession();
                McoClient mcoclient = new McoClient(session.getSessionID(), session.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
                mcoclient.func_148712_c(GuiScreenBackup.this.field_146846_h, this.field_148424_c.field_148780_a);
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException interruptedexception) {
                    Thread.currentThread().interrupt();
                }
                if (this.func_148418_c()) {
                    return;
                }
                this.func_148413_b().displayGuiScreen(GuiScreenBackup.this.field_146842_g);
            } catch (ExceptionMcoService exceptionmcoservice) {
                if (this.func_148418_c()) {
                    return;
                }
                GuiScreenBackup.field_146841_f.error("Couldn't restore backup");
                this.func_148416_a(exceptionmcoservice.toString());
            } catch (Exception exception) {
                if (this.func_148418_c()) {
                    return;
                }
                GuiScreenBackup.field_146841_f.error("Couldn't restore backup?");
                this.func_148416_a(exception.getLocalizedMessage());
            }
        }

        RestoreTask(Backup p_i1102_2_, Object p_i1102_3_) {
            this(p_i1102_2_);
        }
    }

    @SideOnly(Side.CLIENT)
    public class SelectionList extends GuiScreenSelectLocation {

        public SelectionList() {
            super(GuiScreenBackup.this.mc, GuiScreenBackup.this.width, GuiScreenBackup.this.height, 32, GuiScreenBackup.this.height - 64, 36);
        }

        @Override
        protected int func_148355_a() {
            return GuiScreenBackup.this.field_146847_i.size() + 1;
        }

        @Override
        protected void func_148352_a(int p_148352_1_, boolean p_148352_2_) {
            if (p_148352_1_ < GuiScreenBackup.this.field_146847_i.size()) {
                GuiScreenBackup.this.field_146843_s = p_148352_1_;
            }
        }

        @Override
        protected boolean func_148356_a(int p_148356_1_) {
            return p_148356_1_ == GuiScreenBackup.this.field_146843_s;
        }

        @Override
        protected boolean func_148349_b(int p_148349_1_) {
            return false;
        }

        @Override
        protected int func_148351_b() {
            return this.func_148355_a() * 36;
        }

        @Override
        protected void func_148358_c() {
            GuiScreenBackup.this.drawDefaultBackground();
        }

        @Override
        protected void func_148348_a(int p_148348_1_, int p_148348_2_, int p_148348_3_, int p_148348_4_, Tessellator p_148348_5_) {
            if (p_148348_1_ < GuiScreenBackup.this.field_146847_i.size()) {
                this.func_148385_b(p_148348_1_, p_148348_2_, p_148348_3_, p_148348_4_, p_148348_5_);
            }
        }

        private void func_148385_b(int p_148385_1_, int p_148385_2_, int p_148385_3_, int p_148385_4_, Tessellator p_148385_5_) {
            Backup backup = GuiScreenBackup.this.field_146847_i.get(p_148385_1_);
            GuiScreenBackup.this.drawString(GuiScreenBackup.this.fontRendererObj, "Backup (" + GuiScreenBackup.this.func_146829_a(MinecraftServer.getCurrentTimeMillis() - backup.field_148778_b.getTime()) + ")", p_148385_2_ + 2, p_148385_3_ + 1, 16777215);
            GuiScreenBackup.this.drawString(GuiScreenBackup.this.fontRendererObj, this.func_148384_a(backup.field_148778_b), p_148385_2_ + 2, p_148385_3_ + 12, 7105644);
        }

        private String func_148384_a(Date p_148384_1_) {
            return DateFormat.getDateTimeInstance(3, 3).format(p_148384_1_);
        }
    }
}
