package space.libs.mixins;

import net.minecraft.util.RegistrySimple;
import org.spongepowered.asm.mixin.*;

import java.util.Map;

@Mixin(RegistrySimple.class)
public abstract class MixinRegistrySimple {

    @Shadow
    protected @Final Map<String, Object> registryObjects;

}
