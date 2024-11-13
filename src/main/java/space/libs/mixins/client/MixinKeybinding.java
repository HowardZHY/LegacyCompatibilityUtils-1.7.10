package space.libs.mixins.client;

import net.minecraft.client.settings.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import space.libs.util.cursedmixinextensions.annotations.NewConstructor;
import space.libs.util.cursedmixinextensions.annotations.ShadowConstructor;

@SuppressWarnings("unused")
@Mixin(KeyBinding.class)
public class MixinKeybinding {

    @ShadowConstructor
    public void Keybinding(String description, int keyCode, String category) {}

    @NewConstructor
    public void Keybinding(String desc, int key) {
        this.Keybinding(desc, key, "key.categories.gameplay");
    }
}
