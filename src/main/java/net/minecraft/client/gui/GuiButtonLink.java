package net.minecraft.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.net.URI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("all")
@SideOnly(Side.CLIENT)
public class GuiButtonLink extends GuiButton {
    private static final Logger field_146139_o = LogManager.getLogger();

    public GuiButtonLink(int id, int x, int y, int w, int h, String buttonText) {
        super(id, x, y, w, h, buttonText);
    }

    /** openURI */
    public void func_146138_a(String paramString) {
        try {
            URI uri = new URI(paramString);
            Class<?> clazz = Class.forName("java.awt.Desktop");
            Object object = clazz.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
            clazz.getMethod("browse", new Class[] { URI.class }).invoke(object, new Object[] { uri });
        } catch (Throwable throwable) {
            field_146139_o.error("Couldn't open link", throwable);
        }
    }
}
