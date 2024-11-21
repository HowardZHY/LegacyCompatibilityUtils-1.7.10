package space.libs.mixins.entity;

import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import space.libs.interfaces.IPlayer;

@Mixin(EntityPlayer.class)
public class MixinEntityPlayer implements IPlayer {

    public void func_71035_c(String msg) {}

}
