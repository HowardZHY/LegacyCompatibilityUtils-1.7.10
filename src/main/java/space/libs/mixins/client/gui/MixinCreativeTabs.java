package space.libs.mixins.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import space.libs.util.MappedName;

@Mixin(value = CreativeTabs.class, priority = 40)
public abstract class MixinCreativeTabs {

    /**
     * @author HowardZHY
     * @reason Legacy Tab
     */
    @SuppressWarnings("OverwriteModifiers")
    @SideOnly(Side.CLIENT)
    @Overwrite
    public Item getTabIconItem() {
        Item item = Item.getItemById(this.func_78012_e());
        if (item != null) {
            return item;
        }
        return Items.stick;
    }

    @MappedName(value = "getTabIconItemIndex", until = "1.6.4")
    public int func_78012_e() {
        return 2;
    }
}
