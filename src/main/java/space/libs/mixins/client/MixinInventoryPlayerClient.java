package space.libs.mixins.client;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(InventoryPlayer.class)
public abstract class MixinInventoryPlayerClient {

    @Shadow
    private int getInventorySlotContainItemAndDamage(Item item, int damage) {
        throw new AbstractMethodError();
    }

    @Shadow
    public void setCurrentItem(Item item, int damage, boolean b, boolean b1) {}

    public int func_70434_c(int id, int damage) {
        return this.getInventorySlotContainItemAndDamage(Item.getItemById(id), damage);
    }

    public void func_70433_a(int id, int damage, boolean b, boolean b1) {
        this.setCurrentItem(Item.getItemById(id), damage, b, b1);
    }
}
