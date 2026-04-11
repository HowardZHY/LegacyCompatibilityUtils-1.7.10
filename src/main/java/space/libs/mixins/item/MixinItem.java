package space.libs.mixins.item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import space.libs.core.ICoreUtils;
import space.libs.fml.ItemProxy;
import space.libs.util.MappedName;
import space.libs.util.cursedmixinextensions.annotations.*;
import space.libs.util.forge.RegistryUtils;

@SuppressWarnings("unused")
@Mixin(value = Item.class, priority = 20)
public abstract class MixinItem implements ItemProxy, IItem, ICoreUtils {

    @Shadow
    public Item setHasSubtypes(boolean type) {
        throw new AbstractMethodError();
    }

    @Shadow
    public Item setMaxDurability(int value) {
        throw new AbstractMethodError();
    }

    @ShadowConstructor
    public void Item() {}

    @NewConstructor
    public void Item(int id) {
        Item();
        if (id < 1) {
            this.SetLegacyItemNoID("Item");
            return;
        }
        this.SetLegacyItem(id, "Item");
        RegistryUtils.putItem(GetItemInstance(), this.field_77779_bT);
        // TODO? GameData.newItemAdded(this);
    }

    @Inject(method = "setUnlocalizedName", at = @At("RETURN"))
    public void setUnlocalizedName(String name, CallbackInfoReturnable<Item> cir) {
        if (this.LegacyItemNoID) {
            GameRegistry.registerItem(GetItemInstance(), name);
        } else if (this.LegacyItem) {
            LOGGER.info("Name : " + name);
            RegistryUtils.registerLegacyItem(GetItemInstance(), name);
        }
    }

    public boolean LegacyItem;

    public boolean LegacyItemNoID;

    @Override
    public boolean IsLegacyItem() {
        return (LegacyItem || LegacyItemNoID);
    }

    public Item GetItemInstance() {
        return (Item) (Object) this;
    }

    public void SetLegacyItem(int id, String type) {
        this.LegacyItem = true;
        if (field_77698_e[256 + id] != null) {
            LOGGER.error("CONFLICT ID: " + id + " Item slot already occupied by " + field_77698_e[256 + id] + " while adding " + this);
        }
        this.SetLegacyIDRaw(id);
        LOGGER.info("Legacy Register " + type + " ID : " + this.field_77779_bT);
    }

    public void SetLegacyItemNoID(String type) {
        this.LegacyItemNoID = true;
        LOGGER.warn("Legacy Register " + type + " Has Invalid ID : " + this.getClass());
    }

    public void SetLegacyIDRaw(int id) {
        this.SetLegacyID(256 + id);
    }

    @Override
    public void SetLegacyID(int id) {
        this.field_77779_bT = id;
        field_77698_e[id] = this.GetItemInstance();
    }

    /** Legacy Fields */
    @Public
    private static Item[] field_77698_e = new Item[32000];

    @MappedName(value = "itemID", since = "1.6.4")
    public int field_77779_bT;

}
