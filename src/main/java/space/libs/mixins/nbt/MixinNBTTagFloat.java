package space.libs.mixins.nbt;

import net.minecraft.nbt.NBTTagFloat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.io.DataInput;
import java.io.IOException;

@SuppressWarnings("unused")
@Mixin(NBTTagFloat.class)
public class MixinNBTTagFloat extends MixinNBTBase {

    @Shadow
    private float data;

    /** load */
    public void func_74735_a(DataInput input, int paramInt) {
        try {
            this.data = input.readFloat();
        } catch (IOException e) {
            System.out.println("Exception while reading NBT data input : " + e);
        }
    }

}
