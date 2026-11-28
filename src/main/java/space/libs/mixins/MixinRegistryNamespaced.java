package space.libs.mixins;

import net.minecraft.util.RegistryNamespaced;
import org.spongepowered.asm.mixin.*;
import space.libs.interfaces.IRegistryNamespaced;

@Mixin(RegistryNamespaced.class)
public abstract class MixinRegistryNamespaced extends MixinRegistrySimple implements IRegistryNamespaced {

    @Shadow
    public abstract String getNameForObject(Object object);

    @Override
    public void compatlib$removeObject(String key, Object value) {
        this.registryObjects.remove(key, value);
    }

    @Override
    public void compatlib$putObject(String key, Object value) {
        this.registryObjects.put(key, value);
    }
}
