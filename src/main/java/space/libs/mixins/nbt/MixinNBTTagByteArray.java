package space.libs.mixins.nbt;

import net.minecraft.nbt.NBTTagByteArray;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.util.MappedName;

import java.io.DataInput;
import java.io.IOException;

@SuppressWarnings("unused")
@Mixin(NBTTagByteArray.class)
public abstract class MixinNBTTagByteArray extends MixinNBTBase {

    @Shadow
    private byte[] byteArray;

    @MappedName(value = "load", until = "1.7.2")
    public void func_74735_a(DataInput input, int depth) {
        try {
            int i = input.readInt();
            this.byteArray = new byte[i];
            input.readFully(this.byteArray);
        } catch (IOException e) {
            System.out.println("Exception while reading NBT data input : " + e);
        }

    }
}
