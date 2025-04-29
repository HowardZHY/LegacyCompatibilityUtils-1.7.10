package space.libs.mixins;

import net.minecraft.profiler.PlayerUsageSnooper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.*;

@SuppressWarnings("unused")
@Mixin(PlayerUsageSnooper.class)
public abstract class MixinPlayerUsageSnooper {

    public Map<?, ?> field_76482_a = new HashMap<Object, Object>();

    @Shadow
    private void func_152766_h() {}

    @Shadow
    public void addStatToSnooper(String p_152767_1_, Object p_152767_2_) {}

    public void func_76464_f() {
        this.func_152766_h();
    }

    public void func_76472_a(String s, Object o) {
        this.addStatToSnooper(s, o);
    }

}
