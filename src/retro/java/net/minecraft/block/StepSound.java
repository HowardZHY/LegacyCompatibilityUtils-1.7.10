package net.minecraft.block;

@SuppressWarnings("unused")
public class StepSound extends Block.SoundType {

    public String field_72681_a;

    public float field_72679_b;

    public float field_72680_c;

    public StepSound(String name, float volume, float frequency) {
        super(name, volume, frequency);
        this.field_72681_a = name;
        this.field_72679_b = volume;
        this.field_72680_c = frequency;
    }

    public float func_72677_b() {
        return this.field_72679_b;
    }

    public float func_72678_c() {
        return this.field_72680_c;
    }

    public String func_72676_a() {
        return "dig." + this.field_72681_a;
    }

    public String func_72675_d() {
        return "step." + this.field_72681_a;
    }

    public String func_82593_b() {
        return this.func_72676_a();
    }
}
