package space.libs.mixins.mods.mobdis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.HashMap;
import java.util.List;

@Pseudo
@Mixin(targets = "mobdis.client.core.TickHandlerClient", remap = false)
public class MixinTickHandlerClient {

    @Shadow
    public HashMap<EntityLivingBase, Integer> dismemberTimeout;

    @Redirect(
        method = "worldTick",
        at = @At(
            value = "INVOKE",
            target = "Ljava/util/List;get(I)Ljava/lang/Object;"
        )
    )
    public Object worldTick(List<Entity> list, int i) {
        Entity ent = Minecraft.getMinecraft().theWorld.loadedEntityList.get(i);
        if ((ent instanceof AbstractClientPlayer) && !ent.isEntityAlive() && !this.dismemberTimeout.containsKey(ent)) {
            this.dismemberTimeout.put((EntityLivingBase)ent, 2);
        }
        return ent;
    }
}
