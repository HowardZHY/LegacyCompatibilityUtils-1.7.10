package space.libs.mixins.nbt;

import net.minecraft.nbt.*;
import org.spongepowered.asm.mixin.Mixin;
import space.libs.util.MappedName;

import java.io.DataInput;

@SuppressWarnings("all")
@Mixin(NBTTagEnd.class)
public abstract class MixinNBTTagEnd extends MixinNBTBase implements INBTBase {

    @MappedName(value = "load", until = "1.7.2")
    public void func_74735_a(DataInput input, int depth) {}

}
