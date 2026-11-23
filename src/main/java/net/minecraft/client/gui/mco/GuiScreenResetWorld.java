package net.minecraft.client.gui.mco;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.mco.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT) @SuppressWarnings({"unchecked", "unused"})
public class GuiScreenResetWorld extends ScreenWithCallback {

    private static final Logger logger = LogManager.getLogger();

    private GuiScreen field_146742_f;

    private McoServer field_146743_g;

    private GuiTextField field_146749_h;

    private final int field_146750_i = 1;

    private final int field_146747_r = 2;

    private static int field_146746_s = 3;

    private WorldTemplate field_146745_t;

    private GuiButton field_146744_u;

    public GuiScreenResetWorld(GuiScreen par1GuiScreen, McoServer par2McoServer) {
        this.field_146742_f = par1GuiScreen;
        this.field_146743_g = par2McoServer;
    }

    @Override
    public void updateScreen() {
        this.field_146749_h.updateCursorCounter();
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(this.field_146744_u = new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, 97, 20, I18n.format("mco.configure.world.buttons.reset")));
        this.buttonList.add(new GuiButton(2, this.width / 2 + 5, this.height / 4 + 120 + 12, 97, 20, I18n.format("gui.cancel")));
        this.field_146749_h = new GuiTextField(this.fontRendererObj, this.width / 2 - 100, 99, 200, 20);
        this.field_146749_h.setFocused(true);
        this.field_146749_h.setMaxStringLength(32);
        this.field_146749_h.setText("");
        if (this.field_146745_t == null) {
            this.buttonList.add(new GuiButton(field_146746_s, this.width / 2 - 100, 125, 200, 20, I18n.format("mco.template.default.name")));
        } else {
            this.field_146749_h.setText("");
            this.field_146749_h.setEnabled(false);
            this.field_146749_h.setFocused(false);
            this.buttonList.add(new GuiButton(field_146746_s, this.width / 2 - 100, 125, 200, 20, I18n.format("mco.template.name") + ": " + this.field_146745_t.field_148785_b));
        }
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    protected void keyTyped(char par1, int par2) {
        this.field_146749_h.textboxKeyTyped(par1, par2);
        if (par2 == 28 || par2 == 156) {
            this.actionPerformed(this.field_146744_u);
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.enabled) {
            if (button.id == 2) {
                this.mc.displayGuiScreen(this.field_146742_f);
            } else if (button.id == 1) {
                String s = I18n.format("mco.configure.world.reset.question.line1");
                String s1 = I18n.format("mco.configure.world.reset.question.line2");
                this.mc.displayGuiScreen(new GuiScreenConfirmation(this, GuiScreenConfirmation.ConfirmationType.Warning, s, s1, 1));
            } else if (button.id == field_146746_s) {
                this.mc.displayGuiScreen(new GuiScreenMcoWorldTemplate(this, this.field_146745_t));
            }
        }
    }

    @Override
    public void confirmClicked(boolean par1, int par2) {
        if (par1 && par2 == 1) {
            this.func_146741_h();
        } else {
            this.mc.displayGuiScreen(this);
        }
    }

    private void func_146741_h() {
        GuiScreenResetWorld.ResetWorldTask resetworldtask = new GuiScreenResetWorld.ResetWorldTask(this.field_146743_g.field_148812_a, this.field_146749_h.getText(), this.field_146745_t);
        GuiScreenLongRunningTask guiscreenlongrunningtask = new GuiScreenLongRunningTask(this.mc, this.field_146742_f, resetworldtask);
        guiscreenlongrunningtask.func_146902_g();
        this.mc.displayGuiScreen(guiscreenlongrunningtask);
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) {
        super.mouseClicked(par1, par2, par3);
        this.field_146749_h.mouseClicked(par1, par2, par3);
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("mco.reset.world.title"), this.width / 2, 17, 16777215);
        this.drawCenteredString(this.fontRendererObj, I18n.format("mco.reset.world.warning"), this.width / 2, 56, 16711680);
        this.drawString(this.fontRendererObj, I18n.format("mco.reset.world.seed"), this.width / 2 - 100, 86, 10526880);
        this.field_146749_h.drawTextBox();
        super.drawScreen(par1, par2, par3);
    }

    public void func_146740_a(WorldTemplate p_146740_1_) {
        this.field_146745_t = p_146740_1_;
    }

    @Override
    public void func_146735_a(Object p_146735_1_) {
        this.func_146740_a((WorldTemplate)p_146735_1_);
    }

    @SideOnly(Side.CLIENT)
    public class ResetWorldTask extends TaskLongRunning {

        private final long field_148422_c;

        private final String field_148420_d;

        private final WorldTemplate field_148421_e;

        public ResetWorldTask(long par2, String par4Str, WorldTemplate par5WorldTemplate) {
            this.field_148422_c = par2;
            this.field_148420_d = par4Str;
            this.field_148421_e = par5WorldTemplate;
        }

        @Override
        public void run() {
            Session session = GuiScreenResetWorld.this.mc.getSession();
            McoClient mcoclient = new McoClient(session.getSessionID(), session.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
            String s = I18n.format("mco.reset.world.resetting.screen.title");
            this.func_148417_b(s);
            try {
                if (this.func_148418_c()) {
                    return;
                }
                if (this.field_148421_e != null) {
                    mcoclient.func_148696_e(this.field_148422_c, this.field_148421_e.field_148787_a);
                } else {
                    mcoclient.func_148699_d(this.field_148422_c, this.field_148420_d);
                }
                if (this.func_148418_c()) {
                    return;
                }
                GuiScreenResetWorld.this.mc.displayGuiScreen(GuiScreenResetWorld.this.field_146742_f);
            } catch (Exception exception) {
                if (this.func_148418_c()) {
                    return;
                }
                GuiScreenResetWorld.logger.error("Couldn't reset world");
                this.func_148416_a(exception.toString());
            }
        }
    }
}
