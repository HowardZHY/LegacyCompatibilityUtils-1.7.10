package space.libs.mixins.forge.interfaces;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = ISimpleBlockRenderingHandler.class, remap = false)
public interface MixinISimpleBlockRenderingHandler {

    /**
     * @author HowardZHY
     * @reason legacy compat
     */
    @SuppressWarnings("OverwriteModifiers")
    @Overwrite
    default boolean shouldRender3DInInventory(int i) {
        return shouldRender3DInInventory();
    }

    default boolean shouldRender3DInInventory() {
        try {
            return shouldRender3DInInventory(1);
        } catch (StackOverflowError ignored) {
            throw new AbstractMethodError("Bad ISimpleBlockRenderingHandler Impl: ".concat(this.getClass().getName()));
        }
    }

}
