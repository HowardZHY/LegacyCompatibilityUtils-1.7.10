package space.libs.mixins.client;

import com.google.common.collect.Maps;
import net.minecraft.client.resources.AbstractResourcePack;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.libs.util.cursedmixinextensions.annotations.NewConstructor;
import space.libs.util.cursedmixinextensions.annotations.ShadowSuperConstructor;

import java.io.File;
import java.util.*;

@SuppressWarnings("unused")
@Mixin(DefaultResourcePack.class)
public class MixinDefaultResourcePack {

    @Final
    @Shadow
    private Map<String, File> field_152781_b;

    /** mapResourceFiles */
    public Map<String, File> field_110606_b;

    /** fileAssets */
    public File field_110607_c;

    @ShadowSuperConstructor
    public void Object() {}

    @NewConstructor
    public void DefaultResourcePack(File file) {
        Object();
        this.field_110606_b = Maps.newHashMap();
        this.field_110607_c = file;
        this.func_110603_a(this.field_110607_c);
    }

    @Inject(method = "<init>(Ljava/util/Map;)V", at = @At("RETURN"))
    public void DefaultResourcePack(Map<String, File> map, CallbackInfo ci) {
        this.field_110606_b = map;
    }

    /** readAssetsDir */
    public void func_110603_a(File file) {
        if (file.isDirectory()) {
            for (File files : Objects.requireNonNull(file.listFiles()))
                func_110603_a(files);
        } else {
            func_110604_a(AbstractResourcePack.getRelativeName(this.field_110607_c, file), file);
        }
    }

    /** addResourceFile */
    public void func_110604_a(String s, File file) {
        this.field_152781_b.put((new ResourceLocation(s)).toString(), file);
        this.field_110606_b = this.field_152781_b;
    }

}
