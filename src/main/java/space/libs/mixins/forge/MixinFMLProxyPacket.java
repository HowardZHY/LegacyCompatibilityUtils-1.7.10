package space.libs.mixins.forge;

import com.google.common.collect.Lists;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.interfaces.IFMLProxyPacket;
import space.libs.util.cursedmixinextensions.annotations.Public;

import java.io.IOException;
import java.util.*;

@Mixin(value = FMLProxyPacket.class, remap = false)
public class MixinFMLProxyPacket implements IFMLProxyPacket {

    @Shadow
    @Final String channel;

    @Shadow
    private @Final ByteBuf payload;

    @Public
    private static final int PART_SIZE = 0x1000000 - 0x50;

    @Public
    private static final int MAX_LENGTH = Integer.MAX_VALUE - 80;

    public List<Packet> toS3FPackets() throws IOException {
        List<Packet> ret = Lists.newArrayList();
        byte[] data = payload.array();
        if (data.length < PART_SIZE) {
            ret.add(new S3FPacketCustomPayload(channel, payload));
        } else {
            int parts = (int)Math.ceil(data.length / (double)(PART_SIZE - 1)); //We add a byte header so -1
            if (parts > 255) {
                throw new IllegalArgumentException("Payload may not be larger than " + MAX_LENGTH + " bytes");
            }
            PacketBuffer preamble = new PacketBuffer(Unpooled.buffer());
            preamble.writeStringToBuffer(channel);
            preamble.writeByte(parts);
            preamble.writeInt(data.length);
            ret.add(new S3FPacketCustomPayload("FML|MP", preamble));
            int offset = 0;
            for (int x = 0; x < parts; x++) {
                int length = Math.min(PART_SIZE, data.length - offset + 1);
                byte[] tmp = new byte[length];
                tmp[0] = (byte)(x & 0xFF);
                System.arraycopy(data, offset, tmp, 1, tmp.length - 1);
                offset += tmp.length - 1;
                ret.add(new S3FPacketCustomPayload("FML|MP", new PacketBuffer(Unpooled.wrappedBuffer(tmp))));
            }
        }
        return ret;
    }
}
