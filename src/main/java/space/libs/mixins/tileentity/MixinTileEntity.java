package space.libs.mixins.tileentity;

import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.util.cursedmixinextensions.annotations.Public;

import java.util.Map;

@Mixin(TileEntity.class)
public abstract class MixinTileEntity {

    @Shadow
    private static Map<String, Class<? extends TileEntity>> nameToClassMap;

    public Packet func_70319_e() {
        return null;
    }

    @Public
    private static Map<String, Class<? extends TileEntity>> func_85028_t() {
        return nameToClassMap;
    }

}
