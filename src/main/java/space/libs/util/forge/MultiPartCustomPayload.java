/*
 * Forge Mod Loader
 * Copyright (c) 2012-2013 cpw.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 *
 * Contributors:
 *     cpw - implementation
 */

package space.libs.util.forge;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S3FPacketCustomPayload;

import java.io.IOException;

@SuppressWarnings("unused")
public class MultiPartCustomPayload extends S3FPacketCustomPayload {

    public String channel;

    public byte[] data;

    private PacketBuffer data_buf = null;

    private int part_count = 0;

    private int part_expected = 0;

    private int offset = 0;

    public MultiPartCustomPayload(PacketBuffer preamble) throws IOException {
        super(preamble.readStringFromBuffer(20), preamble);
        channel = preamble.readStringFromBuffer(20);
        part_count = preamble.readUnsignedByte();
        int length = preamble.readInt();
        if (length <= 0 || length >= (Integer.MAX_VALUE - 80)) {
            throw new IOException("The received FML MultiPart packet outside of valid length bounds, Max: " + (Integer.MAX_VALUE - 80) + ", Received: " + length);
        }
        data = new byte[length];
        data_buf = new PacketBuffer(Unpooled.wrappedBuffer(data));
    }

    public void processPart(PacketBuffer input) throws IOException {
        int part = (int) (input.readByte() & 0xFF);
        if (part != part_expected) {
            throw new IOException("Received FML MultiPart packet out of order, Expected " + part_expected + " Got " + part);
        }
        int len = input.readableBytes() - 1;
        input.readBytes(data, offset, len);
        part_expected++;
        offset += len;
    }

    public boolean isComplete() {
        return part_expected == part_count;
    }

    @Override
    public String func_149169_c() {
        return this.channel;
    }

    @Override
    public byte[] func_149168_d() {
        return this.data;
    }

    /**
     * getData
     */
    public PacketBuffer func_180735_b() {
        return this.data_buf;
    }
}
