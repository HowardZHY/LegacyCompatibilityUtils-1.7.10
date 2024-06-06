package space.libs.mixins;

import com.mojang.authlib.GameProfile;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.StringUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TileEntitySkull.class)
public class MixinTileEntitySkull {

    @Shadow
    private int field_145908_a;

    @Shadow
    private GameProfile field_152110_j = null;

    public String field_145909_j = "";

    public void func_145905_a(int i, String name) {
        this.field_145908_a = i;
        this.field_145909_j = name;
        if (!StringUtils.isNullOrEmpty(name)) {
            this.field_152110_j = new GameProfile(null, name);
        }
    }

    public String func_145907_c() {
        if (!StringUtils.isNullOrEmpty(this.field_145909_j)) {
            return this.field_145909_j;
        }
        if (this.field_152110_j != null && !StringUtils.isNullOrEmpty(this.field_152110_j.getName())) {
            return this.field_152110_j.getName();
        }
        return "";
    }

}
