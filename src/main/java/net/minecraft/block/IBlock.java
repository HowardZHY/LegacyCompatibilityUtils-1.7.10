package net.minecraft.block;

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

    static BlockFluid GetLegacyFluid(Block block) {
        if (block instanceof BlockFluid) {
            return (BlockFluid) block;
        }
        return null;
    }
}
