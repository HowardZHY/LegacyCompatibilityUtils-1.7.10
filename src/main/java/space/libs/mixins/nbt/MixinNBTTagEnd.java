package space.libs.mixins.nbt;

import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagEnd;
import org.spongepowered.asm.mixin.Mixin;

import java.io.DataInput;

@SuppressWarnings("all")
@Mixin(NBTTagEnd.class)
public class MixinNBTTagEnd implements INBTBase {
    public void func_74735_a(DataInput paramDataInput, int paramInt) {}
}
