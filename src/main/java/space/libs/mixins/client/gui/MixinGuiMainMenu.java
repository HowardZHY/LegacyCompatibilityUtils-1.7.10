package space.libs.mixins.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.libs.util.cursedmixinextensions.annotations.Public;

import java.util.concurrent.atomic.AtomicInteger;

@Mixin(GuiMainMenu.class)
public abstract class MixinGuiMainMenu extends MixinGuiScreen {

    @Final
    @Shadow
    private static Logger logger;

    @Public
    private static boolean field_96139_s;

    @Public
    private static boolean field_96140_r;

    public boolean field_96141_q;

    /** realmsButton*/
    public GuiButton field_130023_H;

    @Public
    private static AtomicInteger field_146973_f = new AtomicInteger(0);

    public GuiButton fmlModButton;

    public void func_130020_g() {
        if (this.field_96141_q) {
            logger.warn("MCO/Realms Availability Checker is INVALID anymore!");
            if (field_96139_s) {
                func_130022_h();
            }
        }
    }

    public void func_130022_h() {
        this.field_130023_H.visible = true;
        this.fmlModButton.width = 98;
        this.fmlModButton.xPosition = super.width / 2 + 2;
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    public void GuiMainMenu(CallbackInfo callbackInfo) {
        field_96139_s = false;
        field_96140_r = true;
        this.field_96141_q = true;
    }
}
