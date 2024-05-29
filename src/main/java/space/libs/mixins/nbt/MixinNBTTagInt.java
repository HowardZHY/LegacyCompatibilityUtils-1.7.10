package space.libs.mixins.nbt;

import net.minecraft.nbt.NBTTagInt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.io.DataInput;
import java.io.IOException;

@SuppressWarnings("unused")
@Mixin(NBTTagInt.class)
public class MixinNBTTagInt extends MixinNBTBase {

    @Shadow
    private int data;

    /** load */
    public void func_74735_a(DataInput input, int paramInt) {
        try {
            this.data = input.readInt();
        } catch (IOException e) {
            System.out.println("Exception while reading NBT data input : " + e);
        }
    }

}
