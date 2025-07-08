package space.libs.mixins.client.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@SideOnly(Side.CLIENT)
@Mixin(value = BlockFlower.class, priority = 4040)
public abstract class MixinBlockFlowerClient extends MixinBlockClient {

    @Inject(method = "getIcon", at = @At("HEAD"), cancellable = true)
    public void getIcon(int side, int meta, CallbackInfoReturnable<IIcon> cir) {
        if (IInstance().IsLegacyBlock()) {
            cir.setReturnValue(super.getIcon(side, meta));
        }
    }

    @Inject(method = "registerIcons", at = @At("HEAD"), cancellable = true)
    public void RegisterIcons(IIconRegister reg, CallbackInfo ci) {
        if (IInstance().IsLegacyBlock()) {
            super.registerIcons(reg);
            ci.cancel();
        }
    }

    @Inject(method = "getSubBlocks", at = @At("HEAD"), cancellable = true)
    public void GetSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list, CallbackInfo ci) {
        if (IInstance().IsLegacyBlock()) {
            this.func_71879_a(Item.getIdFromItem(item), tab, list);
            ci.cancel();
        }
    }
}
