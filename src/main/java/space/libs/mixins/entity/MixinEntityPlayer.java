package space.libs.mixins.entity;

import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.fml.network.Player;
import space.libs.interfaces.IPlayer;
import space.libs.util.MappedName;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer extends MixinEntityLivingBase implements IPlayer, Player {

    @Shadow
    public abstract String getCommandSenderName();

    @MappedName("getEntityName")
    @Override
    public String func_70023_ak() {
        return this.getCommandSenderName();
    }

    public void func_71035_c(String msg) {}

}
