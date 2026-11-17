package space.libs.mixins.world;

import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.interfaces.IWorld;
import space.libs.interfaces.IWorldProvider;

@Mixin(WorldProvider.class)
public abstract class MixinWorldProvider implements IWorldProvider {

    @Shadow
    public World worldObj;

    @Override
    public boolean canSnowAt(int x, int y, int z) {
        return ((IWorld) this.worldObj).canSnowAtBody(x, y, z);
    }
}
