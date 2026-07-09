package space.libs.mixins.interfaces;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.IEntityOwnableName;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import space.libs.CompatLib;

@SuppressWarnings("unused")
@Mixin(IEntityOwnable.class)
public interface MixinIEntityOwnable extends IEntityOwnableName {

    @Override
    default String func_70905_p() {
        return "";
    }

    /**
     * @author HowardZHY
     * @reason No longer abstract
     */
    @SuppressWarnings("OverwriteModifiers")
    @Overwrite
    default String func_152113_b() {
        if (this instanceof Entity) {
            return ((Entity) this).getDataWatcher().getWatchableObjectString(17);
        } else {
            CompatLib.LOGGER.error("Cannot get IEntityOwnable owner's name from non-entity " + this);
            return "";
        }
    }
}
