package space.libs.mixins.mods.mobdis;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.HashMap;

@Pseudo
@Mixin(targets = "mobdis.client.core.TickHandlerClient", remap = false)
public class MixinTickHandlerClient {

    @Shadow
    public HashMap<EntityLivingBase, Integer> dismemberTimeout;

    @Redirect(
        method = "worldTick",
        at = @At(
            value = "INVOKE",
            target = "Ljava/util/ArrayList;get(I)Ljava/lang/Object;"
        )
    )
    public Object worldTick(ArrayList<Entity> e, int i) {
        Entity ent = Minecraft.getMinecraft().theWorld.loadedEntityList.get(i);
        if (ent instanceof EntityPlayer) {
            this.dismemberTimeout.put((EntityLivingBase)ent, 2);
        }
        return ent;
    }
}
