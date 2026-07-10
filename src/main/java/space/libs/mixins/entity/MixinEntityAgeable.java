package space.libs.mixins.entity;

import net.minecraft.entity.EntityAgeable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityAgeable.class)
public abstract class MixinEntityAgeable extends MixinEntityCreature {

}
