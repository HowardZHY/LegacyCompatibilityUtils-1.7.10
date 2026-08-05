package space.libs.mixins;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import space.libs.util.MappedName;
import space.libs.util.forge.ForgeUtils;

import java.util.*;

@SuppressWarnings("unused")
@Mixin(FurnaceRecipes.class)
public abstract class MixinFurnaceRecipes {

    public HashMap<List<Integer>, ItemStack> metaSmeltingList = new HashMap<>();

    public HashMap<List<Integer>, Float> metaExperience = new HashMap<>();

    public Map<Integer, ItemStack> LegacySmeltingList = new HashMap<>();

    @Shadow
    public void addSmelting(Item item, ItemStack stack, float exp) {}

    @Shadow
    public abstract ItemStack getSmeltingResult(ItemStack stack);

    @Shadow
    public abstract float getSmeltingExperience(ItemStack stack);

    @Shadow
    public abstract void addSmeltingRecipe(ItemStack item, ItemStack stack, float exp);

    @MappedName(value = "addSmelting", until = "1.6.4")
    public void func_77600_a(int itemID, ItemStack stack, float exp) {
        this.addSmelting(Item.getItemById(itemID), stack, exp);
    }

    public float func_77601_c(int id) {
        return this.getSmeltingExperience(new ItemStack(Item.getItemById(id)));
    }

    public ItemStack func_77603_b(int id) {
        return this.getSmeltingResult(new ItemStack(Item.getItemById(id)));
    }

    public void addSmelting(int itemID, int metadata, ItemStack itemstack, float experience) {
        this.addSmeltingRecipe(new ItemStack(Item.getItemById(itemID), 1, metadata), itemstack, experience);
        this.metaSmeltingList.put(Arrays.asList(itemID, metadata), itemstack);
        this.metaExperience.put(Arrays.asList(Item.getIdFromItem(itemstack.getItem()), itemstack.getMetadata()), experience);
    }

    public Map<List<Integer>, ItemStack> getMetaSmeltingList() {
        return this.metaSmeltingList;
    }

    public HashMap<List<Integer>, Float> getMetaExperience() {
        return this.metaExperience;
    }

    @Inject(method = "addSmeltingRecipe", at = @At("HEAD"))
    public void addSmeltingRecipe(ItemStack itemIn, ItemStack stack, float exp, CallbackInfo ci) {
        Item item = itemIn.getItem();
        if (item != null) {
            int id = Item.getIdFromItem(item);
            if (id > 0) {
                this.LegacySmeltingList.put(id, stack);
                return;
            }
        }
        LogManager.getLogger().warn("Invalid Smelting: " + itemIn + " " + stack + " " + exp);
    }

    @Inject(method = "getSmeltingList", at = @At("HEAD"), cancellable = true)
    public void getSmeltingList(CallbackInfoReturnable<Map<?, ?>> cir) {
        if (ForgeUtils.getCallerClass(3).startsWith("eec.")) {
            cir.setReturnValue(this.LegacySmeltingList);
        }
    }
}
