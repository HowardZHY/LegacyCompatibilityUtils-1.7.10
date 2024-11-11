package space.libs.mixins.mods.mobdis;

import mobdis.client.entity.EntityGib;
import mobdis.client.render.ModelGib;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
@Mixin(targets = "mobdis.client.render.ModelGib", remap = false)
public abstract class MixinModelGib extends ModelBase {

    /**
     * @author HowardZHY
     * @reason Fix
     */
    @Overwrite
    public void render(Entity ent, float f, float f1, float f2, float f3, float f4, float f5) {
        ModelGib This = (ModelGib) (Object) this;
        if (ent instanceof EntityGib) {
            EntityGib gib = (EntityGib)ent;
            if (gib.type == 0) {
                if (gib.parent instanceof EntitySkeleton) {
                    This.head32.render(f5);
                } else {
                    This.head64.render(f5);
                }
            } else if (gib.type != 1 && gib.type != 2) {
                if (gib.type == 3) {
                    if (gib.parent instanceof EntitySkeleton) {
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
                    This.leg64.render(f5);
                }
            } else if (gib.parent instanceof EntitySkeleton) {
                This.skeleArm.render(f5);
            } else {
                This.arm64.render(f5);
            }
        }
    }
}
