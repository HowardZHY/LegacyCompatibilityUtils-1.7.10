package space.libs.mixins.forge;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.modloader.ModLoaderClientHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourcePack;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.libs.fml.client.KeyBindingRegistry;

import java.util.List;

@SideOnly(Side.CLIENT)
@Mixin(value = FMLClientHandler.class, remap = false)
public class MixinFMLClientHandler {

    @Shadow
    private Minecraft client;

    @Dynamic
    @Inject(
        method = "beginMinecraftLoading",
        at = @At(
            value = "INVOKE",
            target = "Lcpw/mods/fml/common/FMLCommonHandler;beginLoading(Lcpw/mods/fml/common/IFMLSidedHandler;)V",
            shift = At.Shift.AFTER
        )
    )
    public void beginMinecraftLoading(Minecraft minecraft, List<IResourcePack> resourcePackList, IReloadableResourceManager resourceManager, CallbackInfo co) {
        ModLoaderClientHelper.INSTANCE = new ModLoaderClientHelper(minecraft);
    }

    @Dynamic
    @Inject(method = "finishMinecraftLoading", at = @At("TAIL"))
    public void finishMinecraftLoading(CallbackInfo ci) {
        KeyBindingRegistry.instance().uploadKeyBindingsToGame(client.gameSettings);
    }
}
