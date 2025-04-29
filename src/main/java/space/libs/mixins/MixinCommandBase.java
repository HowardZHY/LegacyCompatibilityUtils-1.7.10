package space.libs.mixins;

import net.minecraft.command.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.util.cursedmixinextensions.annotations.Public;

@SuppressWarnings("all")
@Mixin(CommandBase.class)
public abstract class MixinCommandBase {

    @Shadow
    private static IAdminCommand theAdmin;

    /** notifyAdmins */
    @Public
    private static void func_71522_a(ICommandSender sender, String msgFormat, Object... paramVarArgs) {
        func_71524_a(sender, 0, msgFormat, paramVarArgs);
    }

    @Public
    private static void func_71524_a(ICommandSender sender, int paramInt, String msgFormat, Object... paramVarArgs) {
        if (theAdmin != null) {
            IAdminCommandLegacy accessor = (IAdminCommandLegacy) theAdmin;
            accessor.func_71563_a(sender, paramInt, msgFormat, paramVarArgs);
        }
    }
}
