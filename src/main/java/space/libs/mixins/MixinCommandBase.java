package space.libs.mixins;

import net.minecraft.command.CommandBase;
import net.minecraft.command.IAdminCommand;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.util.cursedmixinextensions.annotations.Public;

@SuppressWarnings("all")
@Mixin(CommandBase.class)
public abstract class MixinCommandBase {

    @Shadow
    private static IAdminCommand theAdmin;

    @Shadow
    public static void func_152374_a(ICommandSender sender, ICommand command, int p_152374_2_, String msgFormat, Object ... msgParams) {}

    /** notifyAdmins */
    @Public
    private static void func_71522_a(ICommandSender sender, String msgFormat, Object... paramVarArgs) {
        func_71524_a(sender, 0, msgFormat, paramVarArgs);
    }

    @Public
    private static void func_71524_a(ICommandSender sender, int paramInt, String msgFormat, Object... paramVarArgs) {
        throw new UnsupportedOperationException("Admin Command without ICommand is unsupported");
        //func_152374_a(sender, this, msgFormat, paramVarArgs);
    }
}
