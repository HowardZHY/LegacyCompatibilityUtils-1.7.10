package space.libs.mixins.nbt;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.nbt.INBTBase;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
@Mixin(NBTTagList.class)
public class MixinNBTTagList {

    @Shadow
    private List tagList;

    @Shadow
    private byte tagType;

    public void func_74735_a(DataInput input, int depth) throws IOException {
        if (depth > 512) {
            throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
        }
        this.tagType = input.readByte();
        int j = input.readInt();
        this.tagList = new ArrayList();
        for (int k = 0; k < j; k++) {
            NBTBase nbtbase = NBTBase.func_150284_a(this.tagType);
            INBTBase accessor = (INBTBase) nbtbase;
            accessor.func_74735_a(input, depth + 1);
            this.tagList.add(nbtbase);
        }
    }
}
