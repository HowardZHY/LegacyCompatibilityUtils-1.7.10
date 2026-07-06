package space.libs.mixins;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(InventoryPlayer.class)
public abstract class MixinInventoryPlayer {

    @Shadow
    private int getInventorySlotContainItem(Item item) {
        throw new AbstractMethodError();
    }

    @Shadow
    public abstract boolean consumeInventoryItem(Item item);

    @Shadow
    public abstract boolean hasItem(Item item);

    public int func_70446_h(int id) {
        return this.getInventorySlotContainItem(Item.getItemById(id));
    }

    public boolean func_70435_d(int id) {
        return this.consumeInventoryItem(Item.getItemById(id));
    }

    public boolean func_70450_e(int id) {
        return this.hasItem(Item.getItemById(id));
    }
}
