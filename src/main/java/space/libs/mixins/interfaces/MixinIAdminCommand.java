package space.libs.mixins.interfaces;

import net.minecraft.command.IAdminCommand;
import net.minecraft.command.IAdminCommandLegacy;
import net.minecraft.command.ICommandSender;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(IAdminCommand.class)
public interface MixinIAdminCommand extends IAdminCommandLegacy {
    void func_71563_a(ICommandSender sender, int p_152372_3_, String msgFormat, Object ... msgParams);
}
