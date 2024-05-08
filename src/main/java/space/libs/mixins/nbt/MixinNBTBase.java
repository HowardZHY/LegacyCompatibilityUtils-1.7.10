package space.libs.mixins.nbt;

import net.minecraft.nbt.NBTBase;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.nbt.INBTBase;

import java.io.DataInput;

@Mixin(NBTBase.class)
public abstract class MixinNBTBase implements INBTBase {

    /** load */
    public abstract void func_74735_a(DataInput paramDataInput, int paramInt);

}
