package space.libs.mixins.nbt;

import net.minecraft.nbt.NBTTagIntArray;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.io.DataInput;
import java.io.IOException;

@SuppressWarnings("unused")
@Mixin(NBTTagIntArray.class)
public class MixinNBTTagIntArray extends MixinNBTBase {

    @Shadow
    private int[] intArray;

    public void func_74735_a(DataInput paramDataInput, int paramInt) {
        try {
            int i = paramDataInput.readInt();
            this.intArray = new int[i];
            for (byte b = 0; b < i; b++)
                this.intArray[b] = paramDataInput.readInt();
        } catch (IOException e) {
            System.out.println("Exception while reading NBT data input : " + e);
        }
    }

}
