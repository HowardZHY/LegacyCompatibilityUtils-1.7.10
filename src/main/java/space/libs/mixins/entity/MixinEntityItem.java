package space.libs.mixins.entity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@SuppressWarnings("unused")
@Mixin(EntityItem.class)
public abstract class MixinEntityItem extends MixinEntity {

    @Shadow
    public abstract ItemStack getEntityItem();

    /** getEntityName */
    @Override
    public String func_70023_ak() {
        try {
            return StatCollector.translateToLocal("item." + this.getEntityItem().getUnlocalizedName());
        } catch (Exception e) {
            e.printStackTrace();
            return "item.null.name";
        }
    }
}
