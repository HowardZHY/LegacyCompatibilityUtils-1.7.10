package space.libs.mixins.interfaces;

import net.minecraft.command.IAdminCommand;
import net.minecraft.command.IAdminCommandLegacy;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(IAdminCommand.class)
public interface MixinIAdminCommand extends IAdminCommandLegacy {

    @Override
    default void func_71563_a(ICommandSender sender, int p_152372_3_, String msgFormat, Object ... msgParams) {}

    /**
     * @author HowardZHY
     * @reason default
     */
    @SuppressWarnings("OverwriteModifiers")
    @Overwrite
    default void notifyOperators(ICommandSender sender, ICommand command, int i, String msgFormat, Object... msgParams) {
        this.func_71563_a(sender, i, msgFormat, msgParams);
    }
}
