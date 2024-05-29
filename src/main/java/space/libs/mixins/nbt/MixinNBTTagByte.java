package space.libs.mixins.nbt;

import net.minecraft.nbt.NBTTagByte;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.io.DataInput;
import java.io.IOException;

@SuppressWarnings("unused")
@Mixin(NBTTagByte.class)
public class MixinNBTTagByte extends MixinNBTBase {

    @Shadow
    private byte data;

    /** load */
    public void func_74735_a(DataInput input, int paramInt) {
        try {
            this.data = input.readByte();
        } catch (IOException e) {
            System.out.println("Exception while reading NBT data input : " + e);
        }
    }
}
