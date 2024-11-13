package space.libs.util;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.settings.GameSettings;
import net.minecraftforge.common.util.EnumHelper;
import space.libs.CompatLib;

@SuppressWarnings("rawtypes")
@SideOnly(Side.CLIENT)
public class EnumHelperClientOption extends EnumHelper {

    private static final Class[][] Types = {
        {GameSettings.Options.class, String.class, boolean.class, boolean.class}
    };

    private static final Class[][] TypesOF = {
        {GameSettings.Options.class, String.class, int.class, String.class, boolean.class, boolean.class}
    };

    public static GameSettings.Options addEnum() {
        try {
            return addEnum(Types, GameSettings.Options.class, "USE_SERVER_TEXTURES", "options.serverTextures", false, true);
        } catch (Exception ignored) {
            CompatLib.LOGGER.info("Failed to invoke Vanilla Options Constructor");
        }
        CompatLib.LOGGER.info("Try invoke OptiFine's Options Constructor");
        return addEnum(TypesOF, GameSettings.Options.class, "USE_SERVER_TEXTURES", "USE_SERVER_TEXTURES", 990, "options.serverTextures", false, true);
    }

}
