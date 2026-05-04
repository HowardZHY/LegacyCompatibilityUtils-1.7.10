package space.libs.util.launch;

import net.minecraft.launchwrapper.*;

import java.io.File;
import java.util.List;

@SuppressWarnings("unused")
public class CompatLibLateTweaker implements ITweaker {

    public static void init() {}

    @Override
    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {}

    @Override
    public void injectIntoClassLoader(LaunchClassLoader classLoader) {}

    @Override
    public String getLaunchTarget() {
        return "";
    }

    @Override
    public String[] getLaunchArguments() {
        Launch.classLoader.registerTransformer("space.libs.asm.LateTransformer");
        return new String[0];
    }
}
