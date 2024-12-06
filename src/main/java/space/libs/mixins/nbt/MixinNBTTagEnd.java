package space.libs.mixins.nbt;

import net.minecraft.nbt.*;
import org.spongepowered.asm.mixin.Mixin;

import java.io.DataInput;

@SuppressWarnings("all")
@Mixin(NBTTagEnd.class)
public class MixinNBTTagEnd extends MixinNBTBase implements INBTBase {
    public void func_74735_a(DataInput paramDataInput, int paramInt) {}
}
