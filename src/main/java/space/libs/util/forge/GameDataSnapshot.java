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
import cpw.mods.fml.common.registry.FMLControlledNamespacedRegistry;
import cpw.mods.fml.common.registry.GameData;

import java.util.*;
import java.util.stream.Collectors;

public class GameDataSnapshot {

    public final Map<String, Entry> entries = Maps.newHashMap();

    public static GameDataSnapshot takeSnapshot() {
        GameDataSnapshot snap = new GameDataSnapshot();
        snap.entries.put("fml:blocks", new GameDataSnapshot.Entry(GameData.getBlockRegistry()));
        snap.entries.put("fml:items", new GameDataSnapshot.Entry(GameData.getItemRegistry()));
        return snap;
    }

    public static class Entry {

        public final Map<String, Integer> ids;

        public final Set<String> substitutions;

        public final Map<String, String> aliases;

        public final Set<Integer> blocked;

        @SuppressWarnings("unused")
        public Entry() {
            this(new HashMap<String, Integer>(), new HashSet<String>(), new HashMap<String, String>(), new HashSet<Integer>());
        }

        public Entry(Map<String, Integer> ids, Set<String> substitions, Map<String, String> aliases, Set<Integer> blocked) {
            this.ids = ids;
            this.substitutions = substitions;
            this.aliases = aliases;
            this.blocked = blocked;
        }

        public Entry(FMLControlledNamespacedRegistry<?> registry) {
            this.ids = Maps.newHashMap();
            this.substitutions = Sets.newHashSet();
            this.aliases = Maps.newHashMap();
            this.blocked = Sets.newHashSet();
            registry.serializeInto(this.ids);
            registry.serializeSubstitutions(this.substitutions);
            if (GameData.getBlockRegistry() == registry || GameData.getItemRegistry() == registry) {
                this.blocked.addAll(Arrays.stream(GameData.getBlockedIds()).boxed().collect(Collectors.toList()));
            }
        }
    }

}
