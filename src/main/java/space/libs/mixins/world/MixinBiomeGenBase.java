package space.libs.mixins.world;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.libs.util.MappedName;

@Mixin(BiomeGenBase.class)
public class MixinBiomeGenBase {

    @MappedName(value = "topBlock", until = "1.6.4")
    public byte field_76752_A;

    @MappedName(value = "fillerBlock", until = "1.6.4")
    public byte field_76753_B;

    @Inject(
        method = "<init>(IZ)V",
        at = @At(
            value = "FIELD",
            opcode = Opcodes.PUTFIELD,
            target = "Lnet/minecraft/world/biome/BiomeGenBase;fillerBlock:Lnet/minecraft/block/Block;",
            shift = At.Shift.AFTER
        )
    )
    public void init(int p_i1971_1_, boolean register, CallbackInfo ci) {
        this.field_76752_A = (byte) Block.getIdFromBlock(Blocks.grass);
        this.field_76753_B = (byte) Block.getIdFromBlock(Blocks.dirt);
    }
}
