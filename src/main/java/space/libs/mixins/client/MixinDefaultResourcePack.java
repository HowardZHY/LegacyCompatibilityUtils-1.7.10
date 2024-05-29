package space.libs.mixins.client;

import net.minecraft.client.resources.AbstractResourcePack;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.util.cursedmixinextensions.annotations.NewConstructor;
import space.libs.util.cursedmixinextensions.annotations.ShadowSuperConstructor;

import java.io.File;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("all")
@Mixin(DefaultResourcePack.class)
public class MixinDefaultResourcePack {

    @Final
    @Shadow
    private Map field_152781_b;

    public Map field_110606_b;

    public File field_110607_c;

    @ShadowSuperConstructor
    public void Object() {}

    @NewConstructor
    public void DefaultResourcePack(File paramFile) {
        Object();
        this.field_110607_c = paramFile;
        this.func_110603_a(this.field_110607_c);
    }

    /** readAssetsDir */
    public void func_110603_a(File paramFile) {
        if (paramFile.isDirectory()) {
            for (File file : Objects.requireNonNull(paramFile.listFiles()))
                func_110603_a(file);
        } else {
            func_110604_a(AbstractResourcePack.getRelativeName((File) this.field_152781_b.get(paramFile), paramFile), paramFile);
        }
    }

    /** addResourceFile */
    public void func_110604_a(String paramString, File paramFile) {
        this.field_152781_b.put((new ResourceLocation(paramString)).toString(), paramFile);
        this.field_110606_b = this.field_152781_b;
    }

}
