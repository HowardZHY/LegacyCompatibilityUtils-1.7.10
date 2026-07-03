package space.libs.mixins.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.libs.fml.network.Player;
import space.libs.interfaces.IPlayer;
import space.libs.util.MappedName;
import space.libs.util.cursedmixinextensions.annotations.NewConstructor;
import space.libs.util.cursedmixinextensions.annotations.ShadowConstructor;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer extends MixinEntityLivingBase implements IPlayer, Player {

    public String field_71092_bJ;

    @Shadow
    public abstract String getCommandSenderName();

    @MappedName("getEntityName")
    @Override
    public String func_70023_ak() {
        return this.getCommandSenderName();
    }

    public void func_71035_c(String msg) {}

    @SuppressWarnings("unused")
    @ShadowConstructor
    public void EntityPlayer(World world, GameProfile profile) {}

    @NewConstructor
    public void EntityPlayer(World world, String name) {
        this.EntityPlayer(world, new GameProfile(null, name));
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    public void init(World world, GameProfile profile, CallbackInfo ci) {
        this.field_71092_bJ = profile.getName();
    }
}
