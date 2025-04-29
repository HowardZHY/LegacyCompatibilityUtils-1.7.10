package space.libs.mixins.interfaces;

import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.util.IChatComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@SuppressWarnings("unused")
@Mixin(IBossDisplayData.class)
public interface MixinIBossDisplayData {

    @Shadow
    IChatComponent getFormattedCommandSenderName();

    default String func_70023_ak() {
        return getFormattedCommandSenderName().getUnformattedText();
    }
}
