package space.libs.mixins.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import org.spongepowered.asm.mixin.Mixin;
import space.libs.util.MappedName;
import space.libs.util.cursedmixinextensions.annotations.NewConstructor;
import space.libs.util.cursedmixinextensions.annotations.ShadowConstructor;
import space.libs.util.forge.RegistryUtils;

@SuppressWarnings("unused")
@Mixin(ItemBlock.class)
public abstract class MixinItemBlock extends MixinItem {

    @MappedName(value = "blockID", until = "1.6.4")
    public int field_77885_a;

    @ShadowConstructor
    public void ItemBlock(Block block) {}

    @NewConstructor
    public void ItemBlock(int id) {
        ItemBlock(Block.getBlockById(id + 256));
        if (id < 1) {
            this.SetLegacyItemNoID("ItemBlock");
            return;
        }
        this.SetLegacyItem(id, "ItemBlock");
        RegistryUtils.putItem(GetItemInstance(), id);
    }
}
