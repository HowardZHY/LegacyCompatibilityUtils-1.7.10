package space.libs.mixins.client;

import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.*;
import space.libs.util.cursedmixinextensions.annotations.*;

@SuppressWarnings("unused")
@Mixin(PackMetadataSection.class)
public abstract class MixinPackMetadataSection {

    @Shadow
    public abstract IChatComponent func_152805_a();

    @ShadowSuperConstructor
    public void Object() {}

    @ShadowConstructor
    public void PackMetadataSection(IChatComponent p_i1034_1_, int p_i1034_2_) {}

    @NewConstructor
    public void PackMetadataSection(String p_i1034_1_, int p_i1034_2_) {
        this.PackMetadataSection(IChatComponent.Serializer.jsonToComponent(p_i1034_1_), p_i1034_2_); // Is this correct?
    }

    /** getPackDescription */
    public String func_110461_a() {
        return this.func_152805_a().getFormattedText();
    }

}
