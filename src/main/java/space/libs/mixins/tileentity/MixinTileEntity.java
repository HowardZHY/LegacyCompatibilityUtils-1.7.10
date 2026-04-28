package space.libs.mixins.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import space.libs.util.cursedmixinextensions.annotations.Public;

import java.util.Map;

@Mixin(TileEntity.class)
public abstract class MixinTileEntity {

    @Shadow
    private static Map<String, Class<? extends TileEntity>> nameToClassMap;

    @Inject(method = "getDescriptionPacket", at = @At("RETURN"), cancellable = true)
    public void getDescriptionPacket(CallbackInfoReturnable<net.minecraft.network.Packet> cir) {
        Packet p = this.func_70319_e();
        if (p instanceof Packet132TileEntityData) {
            Packet132TileEntityData data = (Packet132TileEntityData) p;
            NBTTagCompound tagCompound = data.field_73331_e;
            if (tagCompound != null) {
                cir.setReturnValue(new S35PacketUpdateTileEntity(data.field_73334_a, data.field_73332_b, data.field_73333_c, data.field_73330_d, tagCompound));
            }
        }
    }

    public Packet func_70319_e() {
        return null;
    }

    @Public
    private static Map<String, Class<? extends TileEntity>> func_85028_t() {
        return nameToClassMap;
    }

}
