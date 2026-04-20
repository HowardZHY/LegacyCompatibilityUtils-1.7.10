package space.libs.mixins.nbt;

import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagShort;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.util.MappedName;

import java.io.DataInput;
import java.io.IOException;

@SuppressWarnings("unused")
@Mixin(NBTTagShort.class)
public abstract class MixinNBTTagShort extends MixinNBTBase implements INBTBase {

    @Shadow
    private short data;

    @MappedName(value = "load", until = "1.7.2")
    public void func_74735_a(DataInput input, int depth) {
        try {
            this.data = input.readShort();
        } catch (IOException e) {
            System.out.println("Exception while reading NBT data input : " + e);
        }
    }

}
