package space.libs.mixins.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.util.MappedName;
import space.libs.util.cursedmixinextensions.annotations.*;
import space.libs.util.forge.RegistryUtils;

@SuppressWarnings("unused")
@Mixin(BlockFlower.class)
public abstract class MixinBlockFlower extends MixinBlockBush {

    @Shadow
    private int field_149862_O;

    @SuppressWarnings("SameParameterValue")
    @ShadowSuperConstructor
    protected void BlockBush(Material material) {}

    @ShadowSuperConstructor
    public void BlockBush(int id, Material material) {}

    @NewConstructor
    public void BlockFlower() {
        BlockBush(Material.plants);
    }

    @NewConstructor
    public void BlockFlower(int id, Material material) {
        BlockBush(id, material);
    }

    @ReplaceConstructor
    public void BlockFlower(int id) {
        BlockBush(Material.plants);
        if (id < 0) {
            this.SetLegacyBlockNoID("BlockFlower");
            return;
        }
        if (id > 255) {
            this.SetLegacyBlock(id, "BlockFlower");
            RegistryUtils.putBlock(GetBlockInstance(), this.field_71990_ca);
            return;
        }
        this.field_149862_O = id;
    }

    @MappedName("canThisPlantGrowOnThisBlockID")
    public boolean func_72263_d_(int id) {
        return true; // Lazy
    }

    public EnumPlantType getPlantType(World world, int x, int y, int z) {
        return super.getPlantType(world, x, y, z);
    }

    public int getPlantID(World world, int x, int y, int z) {
        return Block.getIdFromBlock(GetBlockInstance());
    }

    public int getPlantMetadata(World world, int x, int y, int z) {
        return super.getPlantMetadata(world, x, y, z);
    }
}
