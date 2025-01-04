package space.libs.mixins.client;

import net.minecraft.client.settings.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.util.cursedmixinextensions.annotations.NewConstructor;
import space.libs.util.cursedmixinextensions.annotations.ShadowConstructor;

@SuppressWarnings("unused")
@Mixin(KeyBinding.class)
public abstract class MixinKeyBinding {

    @ShadowConstructor
    public void KeyBinding(String description, int keyCode, String category) {}

    @NewConstructor
    public void KeyBinding(String desc, int key) {
        this.KeyBinding(desc, key, "key.categories.gameplay");
    }

    @Shadow
    public abstract boolean isPressed();

    public boolean func_74509_c() {
        return this.isPressed();
    }
}
