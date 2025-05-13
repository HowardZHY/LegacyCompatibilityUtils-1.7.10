package space.libs.mixins.forge;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.libs.fml.client.KeyBindingRegistry;

@SideOnly(Side.CLIENT)
@Mixin(value = FMLClientHandler.class, remap = false)
public class MixinFMLClientHandler {

    @Shadow
    private Minecraft client;

    @Dynamic
    @Inject(method = "finishMinecraftLoading", at = @At("TAIL"))
    public void finishMinecraftLoading(CallbackInfo ci) {
        KeyBindingRegistry.instance().uploadKeyBindingsToGame(client.gameSettings);
    }
}
