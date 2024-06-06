package space.libs.mixins.interfaces;

import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.IEntityOwnableName;
import org.spongepowered.asm.mixin.Mixin;

@SuppressWarnings("unused")
@Mixin(IEntityOwnable.class)
public interface MixinIEntityOwnable extends IEntityOwnableName {
    String func_70905_p();
}
