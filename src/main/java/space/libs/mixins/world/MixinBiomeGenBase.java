package space.libs.mixins.world;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenerator;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.libs.util.MappedName;

import java.util.Random;

@Mixin(BiomeGenBase.class)
public abstract class MixinBiomeGenBase {

    @Shadow
    public float temperature;

    @Shadow
    public abstract float getFloatTemperature(int x, int y, int z);

    @Shadow
    public abstract WorldGenAbstractTree func_150567_a(Random random);

    @MappedName(value = "topBlock", until = "1.6.4")
    public byte field_76752_A;

    @MappedName(value = "fillerBlock", until = "1.6.4")
    public byte field_76753_B;

    @MappedName(value = "getIntTemperature", until = "1.6.4")
    public int func_76734_h() {
        return (int) (this.temperature * 65536.0F);
    }

    @MappedName(value = "getRandomWorldGenForTrees", until = "1.6.4")
    public WorldGenerator func_76740_a(Random random) {
        return this.func_150567_a(random);
    }

    @MappedName(value = "getFloatTemperature", until = "1.6.4")
    public float func_76743_j() {
        return this.getFloatTemperature(0, 63, 0);
    }

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
