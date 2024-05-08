package space.libs.mixins.nbt;

import net.minecraft.nbt.NBTTagLong;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.io.DataInput;
import java.io.IOException;

@SuppressWarnings("unused")
@Mixin(NBTTagLong.class)
public class MixinNBTTagLong {

    @Shadow
    private long data;

    /** load */
    public void func_74735_a(DataInput input, int depth) {
        try {
            this.data = input.readLong();
        } catch (IOException e) {
            System.out.println("Exception while reading NBT data input : " + e);
        }
    }

}
