package space.libs.core;

import cpw.mods.fml.relauncher.IFMLCallHook;

import java.util.Map;

@SuppressWarnings("unused")
public class CompatLibSetupHook implements IFMLCallHook, ICoreUtils {

    @Override
    public void injectData(Map<String, Object> data) {}

    @Override
    public Void call() throws Exception {
        LOGGER.info("Mods calling setup hooks...");
        CompatLoader.init();
        return null;
    }
}
