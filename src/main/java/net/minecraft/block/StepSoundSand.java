package net.minecraft.block;

@SuppressWarnings("unused")
public class StepSoundSand extends StepSound {

    public boolean invalid;

    public StepSoundSand(String name, float volume, float frequency) {
        super(name, volume, frequency);
        this.invalid = (name.contains("wood") || name.contains("ladder"));
    }

    public String func_72676_a() {
        if (invalid) {
            return "dig.wood";
        } else {
            return "dig.sand";
        }
    }
}
