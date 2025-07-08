package space.libs.mixins.client.block;

import net.minecraft.block.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import space.libs.util.MappedName;

import java.util.List;

@Mixin(value = Block.class, priority = 4040)
public abstract class MixinBlockClient {

    @MappedName("blockIcon")
    public IIcon field_94336_cN;

    @Shadow
    protected IIcon blockIcon;

    @Shadow
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {}

    @Shadow
    public IIcon getIcon(int side, int meta) {
        throw new AbstractMethodError();
    }

    @Shadow
    public void registerIcons(IIconRegister reg) {}

    @Shadow
    public abstract String getItemIconName();

    @Shadow
    protected abstract String getTextureName();

    public IBlock IInstance() {
        return (IBlock) this;
    }

    public void func_71879_a(int id, CreativeTabs tab, List<ItemStack> list) {
        if (!IInstance().IsLegacyBlock()) {
            this.getSubBlocks(Item.getItemById(id), tab, list);
        }
        list.add(new ItemStack(Item.getItemById(id), 1, 0));
    }

    public void func_94332_a(IIconRegister reg) {
        if (!IInstance().IsLegacyBlock()) {
            this.registerIcons(reg);
        }
        this.blockIcon = reg.registerIcon(this.getTextureName());
        this.field_94336_cN = this.blockIcon;
    }

    public String func_94327_t_() {
        if (!IInstance().IsLegacyBlock()) {
            return this.getItemIconName();
        }
        return null;
    }

    @Inject(method = "getSubBlocks", at = @At("HEAD"), cancellable = true)
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list, CallbackInfo ci) {
        if (IInstance().IsLegacyBlock()) {
            this.func_71879_a(Item.getIdFromItem(itemIn), tab, list);
            ci.cancel();
        }
    }

    @Inject(method = "registerIcons", at = @At("HEAD"), cancellable = true)
    public void registerIcons(IIconRegister reg, CallbackInfo ci) {
        this.field_94336_cN = reg.registerIcon(this.getTextureName());
        if (IInstance().IsLegacyBlock()) {
            this.func_94332_a(reg);
            ci.cancel();
        }
    }

    @Inject(method = "getItemIconName", at = @At("HEAD"), cancellable = true)
    public void getItemIconName(CallbackInfoReturnable<String> cir) {
        if (IInstance().IsLegacyBlock()) {
            cir.setReturnValue(func_94327_t_());
        }
    }
}
