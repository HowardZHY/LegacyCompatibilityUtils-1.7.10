package space.libs.mixins.forge.interfaces;

import com.google.common.io.*;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@SuppressWarnings("all")
@Mixin(value = IEntityAdditionalSpawnData.class, remap = false)
public interface MixinIEntityAdditionalSpawnData {

    /**
     * @author HowardZHY
     * @reason Legacy Data
     */
    @Overwrite
    default void writeSpawnData(ByteBuf byteBuf) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        this.writeSpawnData(out);
        byte[] data = out.toByteArray();
        if (data.length > 0) {
            byteBuf.writeBytes(data);
        }
    }

    /**
     * @author HowardZHY
     * @reason Legacy Data
     */
    @Overwrite
    default void readSpawnData(ByteBuf byteBuf) {
        int l = byteBuf.readableBytes();
        byte[] data = new byte[l];
        if (l > 0) {
            byteBuf.readBytes(data);
        }
        this.readSpawnData(ByteStreams.newDataInput(data));
    }

    default void writeSpawnData(ByteArrayDataOutput data) {}

    default void readSpawnData(ByteArrayDataInput data) {}

}
