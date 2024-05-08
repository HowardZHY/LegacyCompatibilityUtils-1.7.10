package space.libs.mixins;

import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@SuppressWarnings("unused")
@Mixin(EnchantmentDamage.class)
public class MixinEnchantmentDamage {

    @Final
    @Mutable
    @Shadow
    public int damageType;

    /** calcModifierLiving*/
    public float func_77323_a(int paramInt, EntityLivingBase entity) {
        if (this.damageType == 0)
            return paramInt * 1.25F;
        if (this.damageType == 1 && entity.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD)
            return paramInt * 2.5F;
        if (this.damageType == 2 && entity.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD)
            return paramInt * 2.5F;
        return 0.0F;
    }

}
