package space.libs.interfaces;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SuppressWarnings("deprecation")
@SideOnly(Side.CLIENT)
public interface IRenderingRegistry {

    IRenderingRegistry INSTANCE = (IRenderingRegistry) RenderingRegistry.instance();

    int addOverride(String fileToOverride, String fileToAdd);

    void addOverride(String path, String overlayPath, int index);

}
