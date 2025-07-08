/*
 * Forge Mod Loader
 * Copyright (c) 2012-2013 cpw.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * https://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 *
 * Contributors:
 *     cpw - implementation
 */
package space.libs.fml;

import java.util.BitSet;

public class BlockTracker {

    public static final BlockTracker INSTANCE = new BlockTracker();

    public BitSet allocatedBlocks;

    private BlockTracker() {
        allocatedBlocks = new BitSet(4096);
        allocatedBlocks.set(0, 4096);
        //for (int i = 0; i < Block.field_71973_m.length; i++) {
        //    if (Block.field_71973_m[i] != null) {
        //        allocatedBlocks.clear(i);
        //    }
        //}
    }

    public static int nextBlockId() {
        return instance().getNextBlockId();
    }

    public int getNextBlockId() {
        int idx = allocatedBlocks.nextSetBit(0);
        allocatedBlocks.clear(idx);
        return idx;
    }

    public static BlockTracker instance() {
        return INSTANCE;
    }

    public static void reserveBlockId(int id) {
        instance().doReserveId(id);
    }

    public void doReserveId(int id) {
        allocatedBlocks.clear(id);
    }
}
