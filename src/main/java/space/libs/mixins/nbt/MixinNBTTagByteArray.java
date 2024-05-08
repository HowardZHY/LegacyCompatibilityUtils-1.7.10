package space.libs.mixins.nbt;

import net.minecraft.nbt.NBTTagByteArray;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.io.DataInput;
import java.io.IOException;

@SuppressWarnings("unused")
@Mixin(NBTTagByteArray.class)
public class MixinNBTTagByteArray {

    @Shadow
    private byte[] byteArray;

    public void func_74735_a(DataInput paramDataInput, int paramInt) {
        try {
            int i = paramDataInput.readInt();
            this.byteArray = new byte[i];
            paramDataInput.readFully(this.byteArray);
        } catch (IOException e) {
            System.out.println("Exception while reading NBT data input : " + e);
        }

    }
}
