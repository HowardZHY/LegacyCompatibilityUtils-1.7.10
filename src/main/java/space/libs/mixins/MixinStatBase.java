package space.libs.mixins;

import net.minecraft.stats.IStatType;
import net.minecraft.stats.StatBase;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.libs.interfaces.IStatBase;
import space.libs.util.cursedmixinextensions.annotations.NewConstructor;
import space.libs.util.cursedmixinextensions.annotations.ShadowConstructor;

@SuppressWarnings("unused")
@Mixin(value = StatBase.class, priority = 2220)
public class MixinStatBase implements IStatBase {

    @Shadow
    public @Final String statId;

    @Shadow
    public static IStatType simpleStatType;

    public int field_75975_e; // TODO?

    public String field_75978_a;

    @ShadowConstructor
    public void StatBase(String name, IChatComponent p_i45307_2_, IStatType type) {}

    @NewConstructor
    public void StatBase(int id, String name, IStatType type) {
        StatBase(name, new ChatComponentTranslation(name), type);
        this.field_75975_e = id;
    }

    @NewConstructor
    public void StatBase(int id, String name) {
        StatBase(id, name, simpleStatType);
    }

    @Inject(method = "<init>(Ljava/lang/String;Lnet/minecraft/util/IChatComponent;Lnet/minecraft/stats/IStatType;)V", at = @At("RETURN"))
    public void StatBase(String name, IChatComponent p_i45307_2_, IStatType type, CallbackInfo ci) {
        this.field_75978_a = name;
    }

    /** getName */
    public String func_75970_i() {
        return this.statId;
    }

}
