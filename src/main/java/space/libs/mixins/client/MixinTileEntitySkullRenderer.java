package space.libs.mixins.client;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@SuppressWarnings("unused")
@Mixin(TileEntitySkullRenderer.class)
public abstract class MixinTileEntitySkullRenderer extends TileEntitySpecialRenderer {

    @Shadow
    public abstract void func_152674_a(float p_152674_1_, float p_152674_2_, float p_152674_3_, int p_152674_4_, float p_152674_5_, int p_152674_6_, GameProfile p_152674_7_);

    public void func_147530_a(float p_147530_1_, float p_147530_2_, float p_147530_3_, int p_147530_4_, float p_147530_5_, int damage, String name) {
        this.func_152674_a(p_147530_1_, p_147530_2_, p_147530_3_, p_147530_4_, p_147530_5_, damage, new GameProfile(null, name));
    }

}
