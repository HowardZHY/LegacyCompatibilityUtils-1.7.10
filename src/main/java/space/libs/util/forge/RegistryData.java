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

import com.google.common.collect.*;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.handshake.FMLHandshakeMessage;
import io.netty.buffer.ByteBuf;

import java.util.*;

@SuppressWarnings("unused")
public class RegistryData extends FMLHandshakeMessage.ModIdData {

    public RegistryData() {
        super();
    }

    public RegistryData(boolean hasMore, String name, GameDataSnapshot.Entry entry) {
        this.hasMore = hasMore;
        this.name = name;
        this.ids = entry.ids;
        this.substitutions = entry.substitutions;
    }

    private boolean hasMore;

    private String name;

    private Map<String,Integer> ids;

    private Set<String> substitutions;

    @Override
    public void fromBytes(ByteBuf buffer) {
        this.hasMore = buffer.readBoolean();
        this.name = ByteBufUtils.readUTF8String(buffer);
        int length = ByteBufUtils.readVarInt(buffer, 3);
        ids = Maps.newHashMap();

        for (int i = 0; i < length; i++) {
            ids.put(ByteBufUtils.readUTF8String(buffer), ByteBufUtils.readVarInt(buffer, 3));
        }
        length = ByteBufUtils.readVarInt(buffer, 3);
        substitutions = Sets.newHashSet();
        for (int i = 0; i < length; i++) {
            substitutions.add(ByteBufUtils.readUTF8String(buffer));
        }
        //if (!buffer.isReadable()) return; // In case we expand
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        buffer.writeBoolean(this.hasMore);
        ByteBufUtils.writeUTF8String(buffer, this.name);
        ByteBufUtils.writeVarInt(buffer, ids.size(), 3);
        for (Map.Entry<String, Integer> entry: ids.entrySet()) {
            ByteBufUtils.writeUTF8String(buffer, entry.getKey());
            ByteBufUtils.writeVarInt(buffer, entry.getValue(), 3);
        }

        ByteBufUtils.writeVarInt(buffer, substitutions.size(), 3);
        for (String entry: substitutions) {
            ByteBufUtils.writeUTF8String(buffer, entry);
        }
    }

    public Map<String,Integer> getIdMap() {
        return ids;
    }

    public Set<String> getSubstitutions() {
        return substitutions;
    }

    public String getName() {
        return this.name;
    }

    public boolean hasMore() {
        return this.hasMore;
    }

    @Override
    public String toString(Class<? extends Enum<?>> side) {
        return super.toString(side) + ":"+ids.size()+" mappings";
    }
}
