package space.libs.core;

import cpw.mods.fml.relauncher.CoreModManager;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.spongepowered.asm.launch.GlobalProperties;
import org.spongepowered.asm.service.mojang.MixinServiceLaunchWrapper;
import space.libs.util.launch.CompatLibLateTweaker;

import java.io.File;
import java.lang.reflect.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Map;

@IFMLLoadingPlugin.SortingIndex(1001)
public class CompatLibLateCore implements IFMLLoadingPlugin, ICoreUtils {

    public static void init() {
        try {
            Method load = CoreModManager.class.getDeclaredMethod("loadCoreMod", LaunchClassLoader.class, String.class, File.class);
            load.setAccessible(true);
            load.invoke(null, classLoader, "space.libs.core.CompatLibLateCore", null);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        LOGGER.info("Registering Late Transformer");
        try {
            ClassLoader appClassLoader = Launch.class.getClassLoader();
            Method add = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            add.setAccessible(true);
            add.invoke(appClassLoader, this.getClass().getProtectionDomain().getCodeSource().getLocation());
            MethodUtils.invokeStaticMethod(appClassLoader.loadClass(CompatLibLateTweaker.class.getName()), "init");
        } catch (Exception ignored) {}
        final List<String> tweaks = GlobalProperties.get(MixinServiceLaunchWrapper.BLACKBOARD_KEY_TWEAKCLASSES);
        tweaks.add("space.libs.util.launch.CompatLibLateTweaker");
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
