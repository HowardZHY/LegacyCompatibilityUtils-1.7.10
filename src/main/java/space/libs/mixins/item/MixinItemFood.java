package space.libs.mixins.item;

import net.minecraft.item.ItemFood;
import org.spongepowered.asm.mixin.Mixin;
import space.libs.util.cursedmixinextensions.annotations.NewConstructor;
import space.libs.util.cursedmixinextensions.annotations.ShadowConstructor;
import space.libs.util.forge.RegistryUtils;

@SuppressWarnings("unused")
@Mixin(ItemFood.class)
public abstract class MixinItemFood extends MixinItem {

    @ShadowConstructor
    public void ItemFood(int heal, float saturation, boolean wolf) {}

    @NewConstructor
    public void ItemFood(int id, int heal, float saturation, boolean wolf) {
        ItemFood(heal, saturation, wolf);
        if (id <= 0) {
            this.SetLegacyItemNoID("ItemFood");
            return;
        }
        this.SetLegacyItem(id, "Item");
        RegistryUtils.putItem(GetItemInstance(), this.field_77779_bT);
    }

}
