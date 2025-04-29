package space.libs.mixins.client;

import net.minecraft.client.multiplayer.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@SuppressWarnings("unused")
@Mixin(ServerData.class)
public abstract class MixinServerData {

    public boolean field_82823_k;

    @Shadow
    public void setResourceMode(ServerData.ServerResourceMode mode) {}

    @Shadow
    public abstract ServerData.ServerResourceMode getResourceMode();

    /** setAcceptsTextures */
    public void func_78838_a(boolean enabled) {
        if (!enabled) {
            this.setResourceMode(ServerData.ServerResourceMode.DISABLED);
        } else {
            this.setResourceMode(ServerData.ServerResourceMode.ENABLED);
        }
    }

    /** setHideAddress */
    public void func_82819_b(boolean paramBoolean) {
        this.field_82823_k = paramBoolean;
    } // unused

    /** isHidingAddress */
    public boolean func_82820_d() {
        return this.field_82823_k;
    } // unused

    /** getAcceptsTextures */
    public boolean func_147408_b() {
        return this.getResourceMode() != ServerData.ServerResourceMode.DISABLED;
    }

    // unknown
    public boolean func_147410_c() {
        return true;
    }

}
