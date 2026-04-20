package space.libs.mixins.nbt;

import net.minecraft.nbt.NBTTagIntArray;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.util.MappedName;

import java.io.DataInput;
import java.io.IOException;

@SuppressWarnings("unused")
@Mixin(NBTTagIntArray.class)
public abstract class MixinNBTTagIntArray extends MixinNBTBase {

    @Shadow
    private int[] intArray;

    @MappedName(value = "load", until = "1.7.2")
    public void func_74735_a(DataInput input, int depth) {
        try {
            int i = input.readInt();
            this.intArray = new int[i];
            for (byte b = 0; b < i; b++)
                this.intArray[b] = input.readInt();
        } catch (IOException e) {
            System.out.println("Exception while reading NBT data input : " + e);
        }
    }

}
