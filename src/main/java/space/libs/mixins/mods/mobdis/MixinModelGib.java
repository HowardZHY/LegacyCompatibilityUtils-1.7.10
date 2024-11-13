package space.libs.mixins.mods.mobdis;

import mobdis.client.entity.EntityGib;
import mobdis.client.render.ModelGib;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

@Pseudo
@Mixin(targets = "mobdis.client.render.ModelGib", remap = false)
public abstract class MixinModelGib extends ModelBase {

    @Shadow
    public ModelRenderer leg64;

    /**
     * @author HowardZHY
     * @reason Fix
     */
    @Overwrite
    public void render(Entity ent, float f, float f1, float f2, float f3, float f4, float f5) {
        ModelGib This = (ModelGib) (Object) this;
        if (ent instanceof EntityGib) {
            EntityGib gib = (EntityGib) ent;
            boolean b = false;
            if (gib.parent instanceof AbstractClientPlayer) {
                AbstractClientPlayer player = (AbstractClientPlayer) gib.parent;
                try {
                    ResourceLocation location = player.getLocationSkin();
                    InputStream inputStream = Minecraft.getMinecraft().getResourceManager().getResource(location).getInputStream();
                    BufferedImage image = ImageIO.read(inputStream);
                    if (image.getHeight() == 32) {
                        b = true;
                    }
                } catch (Exception ignored) {}
            }
            if (gib.type == 0) {
                if (!b || gib.parent instanceof EntityZombie) {
                    This.head64.render(f5);
                } else {
                    This.head32.render(f5);
                }
            } else if (gib.type != 1 && gib.type != 2) {
                if (gib.type == 3) {
                    if (b || gib.parent instanceof EntitySkeleton) {
                        This.body32.render(f5);
                    } else {
                        This.body64.render(f5);
                    }
                } else if (gib.type != 4 && gib.type != 5) {
                    if (gib.type >= 6) {
                        This.creeperFoot.render(f5);
                    }
                } else if (gib.parent instanceof EntitySkeleton) {
                    This.skeleLeg.render(f5);
                } else {
                    if (b) {
                        leg64.setTextureSize(64, 32);
                    }
                    This.leg64.render(f5);
                }
            } else if (gib.parent instanceof EntitySkeleton) {
                This.skeleArm.render(f5);
            } else {
                if (b) {
                    leg64.setTextureSize(64, 32);
                }
                This.arm64.render(f5);
            }
        }
    }
}
