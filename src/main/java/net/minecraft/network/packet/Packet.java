package net.minecraft.network.packet;

import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.*;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.logging.ILogAgent;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.server.MinecraftServer;
import space.libs.util.MappedName;

import java.io.*;

/**
 * Old, Dummy Packet Class
 */
@SuppressWarnings("unused")
public abstract class Packet extends FMLProxyPacket {

    protected ILogAgent field_98193_m;

    @MappedName("creationTimeMillis")
    public final long field_73295_m = MinecraftServer.getCurrentTimeMillis();

    @MappedName("isChunkDataPacket")
    public boolean field_73287_r = false;

    public Packet() {
        this("UNKNOWN", new byte[0]);
    }

    public Packet(String type, byte[] data) {
        super(Unpooled.wrappedBuffer(data), type);
    }

    @MappedName("writeString")
    public static void func_73271_a(String s, DataOutput output) throws IOException {
        if (s.length() > 32767) {
            throw new IOException("String too big");
        } else {
            output.writeShort(s.length());
            output.writeChars(s);
        }
    }

    @MappedName("readString")
    public static String func_73282_a(DataInput input, int par1) throws IOException {
        short short1 = input.readShort();
        if (short1 > par1) {
            throw new IOException("Received string length longer than maximum allowed (" + short1 + " > " + par1 + ")");
        } else if (short1 < 0) {
            throw new IOException("Received string length is less than zero! Weird string!");
        } else {
            StringBuilder stringbuilder = new StringBuilder();
            for (int j = 0; j < short1; ++j) {
                stringbuilder.append(input.readChar());
            }
            return stringbuilder.toString();
        }
    }

    @MappedName("isRealPacket")
    public boolean func_73278_e() {
        return true;
    }

    @MappedName("containsSameEntityIDAs")
    public boolean func_73268_a(Packet packet) {
        return false;
    }

    @MappedName("canProcessAsync")
    public boolean func_73277_a_() {
        return true;
    }

    @MappedName("readPacketData")
    public void func_73267_a(DataInput input) throws IOException {}

    @MappedName("writePacketData")
    public void func_73273_a(DataOutput output) throws IOException {}

    @MappedName("processPacket")
    public void func_73279_a(NetHandler handler) {}

    @Override
    public void readPacketData(PacketBuffer data) {
        try {
            DataInput in = new DataInputStream(new ByteBufInputStream(data, data.readableBytes()));
            this.func_73267_a(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writePacketData(PacketBuffer data) {
        try {
            DataOutput out = new DataOutputStream(new ByteBufOutputStream(data));
            this.func_73273_a(out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void processPacket(INetHandler handler) {
        if (handler instanceof NetHandler) {
            if (handler instanceof NetServerHandler) {
                this.setTarget(Side.CLIENT);
            } else if (handler instanceof NetClientHandler) {
                this.setTarget(Side.SERVER);
            }
            super.processPacket(handler);
            this.func_73279_a((NetHandler) handler);
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @MappedName("readNBTTagCompound")
    public static NBTTagCompound func_73283_d(DataInput input) throws IOException {
        short short1 = input.readShort();
        if (short1 < 0) {
            return null;
        } else {
            byte[] b = new byte[short1];
            input.readFully(b);
            return CompressedStreamTools.decompress(b, new NBTSizeTracker(2097152L));
        }
    }

    @MappedName("writeNBTTagCompound")
    public static void func_73275_a(NBTTagCompound nbt, DataOutput output) throws IOException {
        if (nbt == null) {
            output.writeShort(-1);
        } else {
            byte[] b = CompressedStreamTools.compress(nbt);
            output.writeShort((short)b.length);
            output.write(b);
        }
    }
}
