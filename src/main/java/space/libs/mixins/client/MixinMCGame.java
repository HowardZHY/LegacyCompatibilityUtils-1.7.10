package space.libs.mixins.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
@Mixin(targets = "me.dags.daflightliteloader.minecraft.MCGame", remap = false)
public class MixinMCGame {

    /**
     * @author HowardZHY
     * @reason Fix NoClip Crash
     */
    @Dynamic
    @Overwrite
    public void setInvulnerable(boolean invulnerable) {
        try {
            AbstractClientPlayer cp = Minecraft.getMinecraft().thePlayer;
            cp.capabilities.disableDamage = invulnerable;
            cp.sendPlayerAbilities();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
