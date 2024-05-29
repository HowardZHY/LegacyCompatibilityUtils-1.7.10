package space.libs.mixins;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerMP.class)
public class MixinEntityPlayerMP {

    public int field_71142_cm;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void EntityPlayerMP(MinecraftServer server, WorldServer p_i45285_2_, GameProfile p_i45285_3_, ItemInWorldManager p_i45285_4_, CallbackInfo ci) {
        this.field_71142_cm = server.getConfigurationManager().getViewDistance();
    }

}
