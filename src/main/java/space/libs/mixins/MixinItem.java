package space.libs.mixins;

import net.minecraft.item.IItem;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.util.cursedmixinextensions.annotations.NewConstructor;
import space.libs.util.cursedmixinextensions.annotations.ShadowSuperConstructor;

import static net.minecraft.item.Item.itemRegistry;

@SuppressWarnings("all")
@Mixin(value = Item.class, priority = 500)
public class MixinItem implements IItem {

    @Shadow
    public static Item getItemById(int p_150899_0_) {
        throw new AbstractMethodError();
    }

    @ShadowSuperConstructor
    public void Object() {}

    @NewConstructor
    public void Item(int id) {
        Object();
        this.field_77779_bT = 256 + id;
        try {
            System.out.println("Old Register Item :");
            System.out.println("ID : " + this.field_77779_bT);
            System.out.println("Name : " + getItemById(field_77779_bT).getUnlocalizedName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // TODO?
    }

    /** 1.6 itemID */
    public int field_77779_bT;

    public void setItemID(int id) {
        this.field_77779_bT = id;
    }

    /**
     * @author HowardZHY
     * @reason 1.6 item id
     */
    @Overwrite
    public static int getIdFromItem(Item item) {
        IItem accessor = (IItem) item;
        if (item == null) {
            accessor.setItemID(0);
            return 0;
        } else {
            accessor.setItemID(itemRegistry.getIDForObject(item));
            return itemRegistry.getIDForObject(item);
        }
    }


}
