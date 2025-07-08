package space.libs.mixins.forge;

import cpw.mods.fml.common.registry.FMLControlledNamespacedRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.IBlock;
import net.minecraft.item.IItem;
import net.minecraft.item.Item;
import net.minecraft.util.RegistryNamespaced;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FMLControlledNamespacedRegistry.class)
public abstract class MixinFMLControlledNamespacedRegistry extends RegistryNamespaced {

    @Inject(method = "addObject", at = @At("RETURN"))
    public void addObject(int id, String name, Object thing, CallbackInfo ci) {
        if (id > 0) {
            if (thing instanceof Block) {
                if (id < 176) {
                    ((IBlock) thing).SetLegacyID(id);
                }
            } else if (thing instanceof Item) {
                if (id < 176 || (id > 255 && id < 423) || (id > 2255 && id < 2268)) {
                    ((IItem) thing).SetLegacyID(id);
                }
            }
        }
    }
}
