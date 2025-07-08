package net.minecraft.block;

@SuppressWarnings("unused")
public class StepSoundAnvil extends StepSound {

    public StepSoundAnvil(String name, float volume, float frequency) {
        super(name, volume, frequency);
    }

    @Override
    public String func_72676_a() {
        return "dig.stone";
    }

    @Override
    public String func_82593_b() {
        return "random.anvil_land";
    }
}
