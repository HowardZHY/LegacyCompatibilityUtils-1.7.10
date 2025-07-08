package net.minecraft.block;

@SuppressWarnings("unused")
public class StepSoundStone extends StepSound {

    public StepSoundStone(String name, float volume, float frequency) {
        super(name, volume, frequency);
    }

    public String func_72676_a() {
        return "random.glass";
    }

    public String func_82593_b() {
        return "step.stone";
    }
}
