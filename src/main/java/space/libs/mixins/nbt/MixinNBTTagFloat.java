package space.libs.mixins.nbt;

import net.minecraft.nbt.NBTTagFloat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.util.MappedName;

import java.io.DataInput;
import java.io.IOException;

@SuppressWarnings("unused")
@Mixin(NBTTagFloat.class)
public abstract class MixinNBTTagFloat extends MixinNBTBase {

    @Shadow
    private float data;

    @MappedName(value = "load", until = "1.7.2")
    public void func_74735_a(DataInput input, int depth) {
        try {
            this.data = input.readFloat();
        } catch (IOException e) {
            System.out.println("Exception while reading NBT data input : " + e);
        }
    }

}
