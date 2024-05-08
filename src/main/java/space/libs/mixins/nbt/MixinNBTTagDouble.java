package space.libs.mixins.nbt;

import net.minecraft.nbt.NBTTagDouble;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.io.DataInput;
import java.io.IOException;

@SuppressWarnings("unused")
@Mixin(NBTTagDouble.class)
public class MixinNBTTagDouble {

    @Shadow
    private double data;

    /** load */
    public void func_74735_a(DataInput input, int paramInt) {
        try {
            this.data = input.readDouble();
        } catch (IOException e) {
            System.out.println("Exception while reading NBT data input : " + e);
        }
    }
}
