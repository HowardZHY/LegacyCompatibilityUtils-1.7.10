package space.libs.mixins;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.util.cursedmixinextensions.annotations.NewConstructor;
import space.libs.util.cursedmixinextensions.annotations.Public;
import space.libs.util.cursedmixinextensions.annotations.ShadowConstructor;

@SuppressWarnings("unused")
@Mixin(value = Block.class, priority = 200)
public abstract class MixinBlock implements IBlock {

    @Shadow
    public abstract boolean isOpaqueCube();

    @ShadowConstructor
    protected void Block(Material materialIn) {}

    @NewConstructor
    public void Block(int id, Material material) {
        Block(material);
        if (field_71973_m[id] != null) {
            throw new IllegalArgumentException("Block ID " + id + " is already occupied by " + field_71973_m[id] + " when adding " + this);
        }
        field_71973_m[id] = this.getBlockInstance();
        this.field_71990_ca = id;
    }

    @SuppressWarnings("all")
    @Public
    private static Block[] field_71973_m = new Block[4096];

    /** 1.6 blockID */
    public int field_71990_ca;

    public Block getBlockInstance() {
        return (Block) (Object) this;
    }

    public boolean func_71926_d() {
        return this.isOpaqueCube();
    }

    /** Legacy Block Fields */
    @Public private static final Block field_71938_D = Blocks.lava;
    @Public private static final Block field_71939_E = Blocks.sand;
    @Public private static final Block field_71940_F = Blocks.gravel;
    @Public private static final Block field_71941_G = Blocks.gold_ore;
    @Public private static final Block field_71942_A = Blocks.flowing_water; //BlockFluid
    @Public private static final Block field_71943_B = Blocks.water;
    @Public private static final Block field_71944_C = Blocks.flowing_lava; //BlockFluid
    @Public private static final Block field_71945_L = Blocks.sponge;
    @Public private static final Block field_71946_M = Blocks.glass;
    @Public private static final Block field_71947_N = Blocks.lapis_ore;
    @Public private static final Block field_71948_O = Blocks.lapis_block;
    @Public private static final Block field_71950_I = Blocks.coal_ore;
    @Public private static final BlockLeaves field_71952_K = Blocks.leaves;
    @Public private static final Block field_71954_T = Blocks.golden_rail;
    @Public private static final Block field_71955_W = Blocks.web;
    @Public private static final BlockPistonBase field_71956_V = Blocks.sticky_piston;
    @Public private static final Block field_71957_Q = Blocks.sandstone;
    @Public private static final Block field_71958_P = Blocks.dispenser;
    @Public private static final Block field_71959_S = Blocks.bed;
    @Public private static final Block field_71960_R = Blocks.noteblock;
    @Public private static final BlockDeadBush field_71961_Y = Blocks.deadbush;
    @Public private static final BlockTallGrass field_71962_X = Blocks.tallgrass;
    @Public private static final BlockPistonBase field_71963_Z = Blocks.piston;
    @Public private static final Block field_71979_v = Blocks.dirt;
    @Public private static final Block field_71980_u = Blocks.grass;
    @Public private static final Block field_71981_t = Blocks.stone;
    @Public private static final Block field_71986_z = Blocks.bedrock;
    @Public private static final Block field_71987_y = Blocks.sapling;
    @Public private static final Block field_71988_x = Blocks.planks;
    @Public private static final Block field_71991_bz = Blocks.waterlily;
    @Public private static final Block field_71992_bw = Blocks.brick_stairs;
    @Public private static final Block field_71993_bv = Blocks.fence_gate;
    @Public private static final BlockMycelium field_71994_by = Blocks.mycelium;
    @Public private static final Block field_71995_bx = Blocks.stone_brick_stairs;
    @Public private static final Block field_71996_bs = Blocks.pumpkin_stem;
    @Public private static final Block field_71997_br = Blocks.melon_block;
    @Public private static final Block field_71998_bu = Blocks.vine;
    @Public private static final Block field_71999_bt = Blocks.melon_stem;
    @Public private static final Block field_72000_bn = Blocks.brown_mushroom_block;
    @Public private static final Block field_72001_bo = Blocks.red_mushroom_block;
    @Public private static final Block field_72002_bp = Blocks.iron_bars;
    @Public private static final Block field_72003_bq = Blocks.glass_pane;
    @Public private static final Block field_72004_bj = Blocks.stained_glass;
    @Public private static final Block field_72005_bk = Blocks.trapdoor;
    @Public private static final Block field_72006_bl = Blocks.monster_egg;
    @Public private static final Block field_72007_bm = Blocks.stonebrick;
    @Public private static final Block field_72008_bf = Blocks.lit_pumpkin;
    @Public private static final Block field_72009_bg = Blocks.cake;
    @Public private static final BlockRedstoneRepeater field_72010_bh = Blocks.unpowered_repeater;
    @Public private static final BlockRedstoneRepeater field_72011_bi = Blocks.powered_repeater;
    @Public private static final Block field_72012_bb = Blocks.netherrack;
    @Public private static final Block field_72013_bc = Blocks.soul_sand;
    @Public private static final Block field_72014_bd = Blocks.glowstone;
    @Public private static final BlockPortal field_72015_be = Blocks.portal;
    @Public private static final Block field_72031_aZ = Blocks.fence;
    @Public private static final Block field_72032_aY = Blocks.jukebox;
    @Public private static final Block field_72033_bA = Blocks.nether_brick;
    @Public private static final Block field_72034_aR = Blocks.stone_button;
    @Public private static final Block field_72035_aQ = Blocks.redstone_torch;
    @Public private static final Block field_72036_aT = Blocks.ice;
    @Public private static final Block field_72037_aS = Blocks.snow_layer;
    @Public private static final Block field_72038_aV = Blocks.cactus;
    @Public private static final Block field_72039_aU = Blocks.snow;
    @Public private static final Block field_72040_aX = Blocks.reeds;
    @Public private static final Block field_72041_aW = Blocks.clay;
    @Public private static final Block field_72042_aI = Blocks.wall_sign;
    @Public private static final Block field_72043_aJ = Blocks.lever;
    @Public private static final Block field_72044_aK = Blocks.stone_pressure_plate;
    @Public private static final Block field_72045_aL = Blocks.iron_door;
    @Public private static final Block field_72046_aM = Blocks.wooden_pressure_plate;
    @Public private static final Block field_72047_aN = Blocks.redstone_ore;
    @Public private static final Block field_72048_aO = Blocks.lit_redstone_ore;
    @Public private static final Block field_72049_aP = Blocks.unlit_redstone_torch;
    @Public private static final Block field_72051_aB = Blocks.furnace;
    @Public private static final Block field_72052_aC = Blocks.lit_furnace;
    @Public private static final Block field_72053_aD = Blocks.standing_sign;
    @Public private static final Block field_72054_aE = Blocks.wooden_door;
    @Public private static final Block field_72055_aF = Blocks.ladder;
    @Public private static final Block field_72056_aG = Blocks.rail;
    @Public private static final Block field_72057_aH = Blocks.stone_stairs;
    @Public private static final Block field_72058_az = Blocks.wheat;
    @Public private static final Block field_72060_ay = Blocks.crafting_table;
    @Public private static final Block field_72061_ba = Blocks.pumpkin;
    @Public private static final Block field_72062_bU = Blocks.tripwire;
    @Public private static final Block field_72063_at = Blocks.oak_stairs;
    @Public private static final Block field_72064_bT = Blocks.tripwire_hook; //BlockTripWireSource
    @Public private static final Block field_72065_as = Blocks.mob_spawner;
    @Public private static final Block field_72066_bS = Blocks.ender_chest;
    @Public private static final BlockFire field_72067_ar = Blocks.fire;
    @Public private static final Block field_72068_bR = Blocks.emerald_ore;
    @Public private static final Block field_72070_bY = Blocks.jungle_stairs;
    @Public private static final Block field_72071_ax = Blocks.diamond_block;
    @Public private static final Block field_72072_bX = Blocks.birch_stairs;
    @Public private static final Block field_72073_aw = Blocks.diamond_ore;
    @Public private static final Block field_72074_bW = Blocks.spruce_stairs;
    @Public private static final BlockRedstoneWire field_72075_av = Blocks.redstone_wire;
    @Public private static final Block field_72076_bV = Blocks.emerald_block;
    @Public private static final BlockChest field_72077_au = Blocks.chest;
    @Public private static final Block field_72078_bL = Blocks.redstone_lamp;
    @Public private static final Block field_72079_ak = Blocks.stone_slab; //BlockHalfSlab
    @Public private static final Block field_72080_bM = Blocks.lit_redstone_lamp;
    @Public private static final Block field_72081_al = Blocks.brick_block;
    @Public private static final Block field_72082_bJ = Blocks.end_stone;
    @Public private static final Block field_72083_ai = Blocks.iron_block;
    @Public private static final Block field_72084_bK = Blocks.dragon_egg;
    @Public private static final Block field_72085_aj = Blocks.double_stone_slab; //BlockHalfSlab
    @Public private static final Block field_72086_bP = Blocks.cocoa;
    @Public private static final Block field_72087_ao = Blocks.mossy_cobblestone;
    @Public private static final Block field_72088_bQ = Blocks.sandstone_stairs;
    @Public private static final Block field_72089_ap = Blocks.obsidian;
    @Public private static final Block field_72090_bN = Blocks.double_wooden_slab; //BlockHalfSlab
    @Public private static final Block field_72091_am = Blocks.tnt;
    @Public private static final Block field_72092_bO = Blocks.wooden_slab; //BlockHalfSlab
    @Public private static final Block field_72093_an = Blocks.bookshelf;
    @Public private static final Block field_72094_bD = Blocks.nether_wart;
    @Public private static final BlockPistonMoving field_72095_ac = Blocks.piston_extension;
    @Public private static final Block field_72096_bE = Blocks.enchanting_table;
    @Public private static final BlockFlower field_72097_ad = Blocks.yellow_flower;
    @Public private static final Block field_72098_bB = Blocks.nether_brick_fence;
    @Public private static final BlockPistonExtension field_72099_aa = Blocks.piston_head;
    @Public private static final Block field_72100_bC = Blocks.nether_brick_stairs;
    @Public private static final Block field_72101_ab = Blocks.wool;
    @Public private static final Block field_72102_bH = Blocks.end_portal;
    @Public private static final Block field_72103_ag = Blocks.red_mushroom; //BlockFlower
    @Public private static final Block field_72104_bI = Blocks.end_portal_frame;
    @Public private static final Block field_72105_ah = Blocks.gold_block;
    @Public private static final Block field_72106_bF = Blocks.brewing_stand;
    @Public private static final BlockFlower field_72107_ae = Blocks.red_flower;
    @Public private static final BlockCauldron field_72108_bG = Blocks.cauldron;
    @Public private static final Block field_72109_af = Blocks.brown_mushroom; //BlockFlower
    @Public private static final Block field_82510_ck = Blocks.anvil;
    @Public private static final Block field_82511_ci = Blocks.wooden_button;
    @Public private static final Block field_82512_cj = Blocks.skull;
    @Public private static final Block field_82513_cg = Blocks.carrots;
    @Public private static final Block field_82514_ch = Blocks.potatoes;
    @Public private static final Block field_82515_ce = Blocks.cobblestone_wall;
    @Public private static final Block field_82516_cf = Blocks.flower_pot;
    @Public private static final Block field_82517_cc = Blocks.command_block;
    @Public private static final BlockBeacon field_82518_cd = Blocks.beacon;
    @Public private static final Block field_94337_cv = Blocks.activator_rail;
    @Public private static final Block field_94338_cu = Blocks.quartz_stairs;
    @Public private static final Block field_94339_ct = Blocks.quartz_block;
    @Public private static final BlockHopper field_94340_cs = Blocks.hopper;
    @Public private static final Block field_94341_cq = Blocks.redstone_block;
    @Public private static final Block field_94342_cr = Blocks.quartz_ore;
    @Public private static final Block field_94343_co = Blocks.powered_comparator; //BlockComparator
    @Public private static final BlockDaylightDetector field_94344_cp = Blocks.daylight_detector;
    @Public private static final Block field_94345_cm = Blocks.heavy_weighted_pressure_plate;
    @Public private static final Block field_94346_cn = Blocks.unpowered_comparator; //BlockComparator
    @Public private static final Block field_94347_ck = Blocks.trapped_chest;
    @Public private static final Block field_94348_cl = Blocks.light_weighted_pressure_plate;
    @Public private static final Block field_96469_cy = Blocks.dropper;
    @Public private static final Block field_111031_cC = Blocks.carpet;
    @Public private static final Block field_111032_cD = Blocks.hardened_clay;
    @Public private static final Block field_111034_cE = Blocks.coal_block;
    @Public private static final Block field_111038_cB = Blocks.hay_block;
    @Public private static final Block field_111039_cA = Blocks.stained_hardened_clay;
}
