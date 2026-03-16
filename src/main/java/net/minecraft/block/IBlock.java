package net.minecraft.block;

import space.libs.CompatLib;
import space.libs.util.forge.RegistryUtils;

public interface IBlock {

    boolean IsLegacyBlock();

    void SetLegacyBlock(int id, String type);

    void SetLegacyBlockNoID(String type);

    int GetLegacyID();

    void SetLegacyID(int id);

    static void InitLegacyBlock(Block block, int id, String type) {
        IBlock This = (IBlock) block;
        if (id <= 0) {
            This.SetLegacyBlockNoID(type);
            return;
        }
        This.SetLegacyBlock(id, type);
        RegistryUtils.putBlock(block, This.GetLegacyID());
    }

    static BlockFlower GetLegacyFlower(Block block) {
        if (block instanceof BlockFlower) {
            return (BlockFlower) block;
        }
        CompatLib.LOGGER.warn("Could get BlockFlower for: " + block.getClass() + " extends " + block.getClass().getSuperclass());
        return null;
    }

    static BlockFluid GetLegacyFluid(Block block) {
        if (block instanceof BlockFluid) {
            return (BlockFluid) block;
        }
        CompatLib.LOGGER.warn("Couldn't get BlockFluid for: " + block.getClass() + " extends " + block.getClass().getSuperclass());
        return null;
    }
}
