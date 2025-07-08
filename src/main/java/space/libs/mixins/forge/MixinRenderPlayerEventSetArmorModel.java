package space.libs.mixins.forge;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderPlayerEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SideOnly(Side.CLIENT)
@Mixin(value = RenderPlayerEvent.SetArmorModel.class, remap = false)
public class MixinRenderPlayerEventSetArmorModel {

    public float partialTick;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void init(EntityPlayer player, RenderPlayer renderer, int slot, float partialTick, ItemStack stack, CallbackInfo ci) {
        this.partialTick = partialTick;
    }

}
