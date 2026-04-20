package space.libs.mixins.nbt;

import net.minecraft.nbt.NBTTagString;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.util.MappedName;

import java.io.DataInput;
import java.io.IOException;

@SuppressWarnings("unused")
@Mixin(NBTTagString.class)
public abstract class MixinNBTTagString extends MixinNBTBase {

    @Shadow
    private String data;

    @MappedName(value = "load", until = "1.7.2")
    public void func_74735_a(DataInput input, int depth) {
        try {
            this.data = input.readUTF();
        } catch (IOException e) {
            System.out.println("Exception while reading NBT data input : " + e);
        }
    }

}
