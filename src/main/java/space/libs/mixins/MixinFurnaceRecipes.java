package space.libs.mixins;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.util.MappedName;

import java.util.*;

@Mixin(FurnaceRecipes.class)
public abstract class MixinFurnaceRecipes {

    public HashMap<List<Integer>, ItemStack> metaSmeltingList = new HashMap<>();

    public HashMap<List<Integer>, Float> metaExperience = new HashMap<>();

    @Shadow
    public void addSmelting(Item item, ItemStack stack, float exp) {}

    @Shadow
    public abstract ItemStack getSmeltingResult(ItemStack stack);

    @Shadow
    public abstract float getSmeltingExperience(ItemStack stack);

    @Shadow
    public abstract void addSmeltingRecipe(ItemStack p_151394_1_, ItemStack p_151394_2_, float p_151394_3_);

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
}
