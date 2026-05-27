package space.libs.mixins;

import net.minecraft.command.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.util.MappedName;
import space.libs.util.cursedmixinextensions.annotations.Public;

@Mixin(CommandBase.class)
public abstract class MixinCommandBase {

    @Shadow
    private static IAdminCommand theAdmin;

    @MappedName("notifyAdmins")
    @Public
    private static void func_71522_a(ICommandSender sender, String msgFormat, Object... args) {
        func_71524_a(sender, 0, msgFormat, args);
    }

    @Public
    private static void func_71524_a(ICommandSender sender, int paramInt, String msgFormat, Object... args) {
        if (theAdmin != null) {
            IAdminCommandLegacy accessor = (IAdminCommandLegacy) theAdmin;
            accessor.func_71563_a(sender, paramInt, msgFormat, args);
        }
    }
}
