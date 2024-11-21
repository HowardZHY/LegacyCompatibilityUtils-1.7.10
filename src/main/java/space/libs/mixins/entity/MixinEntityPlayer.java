package space.libs.mixins.entity;

import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.interfaces.IPlayer;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer extends MixinEntityLivingBase implements IPlayer {

    @Shadow
    public abstract String getCommandSenderName();

    /** getEntityName */
    @Override
    public String func_70023_ak() {
        return this.getCommandSenderName();
    }

    public void func_71035_c(String msg) {}

}
