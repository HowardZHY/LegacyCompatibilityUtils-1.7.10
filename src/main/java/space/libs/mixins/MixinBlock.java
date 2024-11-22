package space.libs.mixins;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.libs.util.cursedmixinextensions.annotations.NewConstructor;
import space.libs.util.cursedmixinextensions.annotations.Public;
import space.libs.util.cursedmixinextensions.annotations.ShadowConstructor;

@SuppressWarnings("unused")
@Mixin(value = Block.class, priority = 200)
public abstract class MixinBlock implements IBlock {

    @Shadow
    public abstract boolean isOpaqueCube();

    @Shadow
    public abstract boolean canPlaceBlockAt(World worldIn, int x, int y, int z);

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

    public boolean func_71930_b(World worldIn, int x, int y, int z) {
        return this.canPlaceBlockAt(worldIn, x, y, z);
    }

    /** Legacy Block Fields */
    @Public private static Block field_71938_D;
    @Public private static Block field_71939_E;
    @Public private static Block field_71940_F;
    @Public private static Block field_71941_G;
    @Public private static Block field_71942_A;
    @Public private static Block field_71943_B;
    @Public private static Block field_71944_C;
    @Public private static Block field_71945_L;
    @Public private static Block field_71946_M;
    @Public private static Block field_71947_N;
    @Public private static Block field_71948_O;
    @Public private static Block field_71950_I;
    @Public private static BlockLeaves field_71952_K;
    @Public private static Block field_71954_T;
    @Public private static Block field_71955_W;
    @Public private static BlockPistonBase field_71956_V;
    @Public private static Block field_71957_Q;
    @Public private static Block field_71958_P;
    @Public private static Block field_71959_S;
    @Public private static Block field_71960_R;
    @Public private static BlockDeadBush field_71961_Y;
    @Public private static BlockTallGrass field_71962_X;
    @Public private static BlockPistonBase field_71963_Z;
    @Public private static Block field_71979_v;
    @Public private static Block field_71980_u;
    @Public private static Block field_71981_t;
    @Public private static Block field_71986_z;
    @Public private static Block field_71987_y;
    @Public private static Block field_71988_x;
    @Public private static Block field_71991_bz;
    @Public private static Block field_71992_bw;
    @Public private static Block field_71993_bv;
    @Public private static BlockMycelium field_71994_by;
    @Public private static Block field_71995_bx;
    @Public private static Block field_71996_bs;
    @Public private static Block field_71997_br;
    @Public private static Block field_71998_bu;
    @Public private static Block field_71999_bt;
    @Public private static Block field_72000_bn;
    @Public private static Block field_72001_bo;
    @Public private static Block field_72002_bp;
    @Public private static Block field_72003_bq;
    @Public private static Block field_72004_bj;
    @Public private static Block field_72005_bk;
    @Public private static Block field_72006_bl;
    @Public private static Block field_72007_bm;
    @Public private static Block field_72008_bf;
    @Public private static Block field_72009_bg;
    @Public private static BlockRedstoneRepeater field_72010_bh;
    @Public private static BlockRedstoneRepeater field_72011_bi;
    @Public private static Block field_72012_bb;
    @Public private static Block field_72013_bc;
    @Public private static Block field_72014_bd;
    @Public private static BlockPortal field_72015_be;
    @Public private static Block field_72031_aZ;
    @Public private static Block field_72032_aY;
    @Public private static Block field_72033_bA;
    @Public private static Block field_72034_aR;
    @Public private static Block field_72035_aQ;
    @Public private static Block field_72036_aT;
    @Public private static Block field_72037_aS;
    @Public private static Block field_72038_aV;
    @Public private static Block field_72039_aU;
    @Public private static Block field_72040_aX;
    @Public private static Block field_72041_aW;
    @Public private static Block field_72042_aI;
    @Public private static Block field_72043_aJ;
    @Public private static Block field_72044_aK;
    @Public private static Block field_72045_aL;
    @Public private static Block field_72046_aM;
    @Public private static Block field_72047_aN;
    @Public private static Block field_72048_aO;
    @Public private static Block field_72049_aP;
    @Public private static Block field_72051_aB;
    @Public private static Block field_72052_aC;
    @Public private static Block field_72053_aD;
    @Public private static Block field_72054_aE;
    @Public private static Block field_72055_aF;
    @Public private static Block field_72056_aG;
    @Public private static Block field_72057_aH;
    @Public private static Block field_72058_az;
    @Public private static Block field_72060_ay;
    @Public private static Block field_72061_ba;
    @Public private static Block field_72062_bU;
    @Public private static Block field_72063_at;
    @Public private static Block field_72064_bT;
    @Public private static Block field_72065_as;
    @Public private static Block field_72066_bS;
    @Public private static BlockFire field_72067_ar;
    @Public private static Block field_72068_bR;
    @Public private static Block field_72070_bY;
    @Public private static Block field_72071_ax;
    @Public private static Block field_72072_bX;
    @Public private static Block field_72073_aw;
    @Public private static Block field_72074_bW;
    @Public private static BlockRedstoneWire field_72075_av;
    @Public private static Block field_72076_bV;
    @Public private static BlockChest field_72077_au;
    @Public private static Block field_72078_bL;
    @Public private static Block field_72079_ak;
    @Public private static Block field_72080_bM;
    @Public private static Block field_72081_al;
    @Public private static Block field_72082_bJ;
    @Public private static Block field_72083_ai;
    @Public private static Block field_72084_bK;
    @Public private static Block field_72085_aj;
    @Public private static Block field_72086_bP;
    @Public private static Block field_72087_ao;
    @Public private static Block field_72088_bQ;
    @Public private static Block field_72089_ap;
    @Public private static Block field_72090_bN;
    @Public private static Block field_72091_am;
    @Public private static Block field_72092_bO;
    @Public private static Block field_72093_an;
    @Public private static Block field_72094_bD;
    @Public private static BlockPistonMoving field_72095_ac;
    @Public private static Block field_72096_bE;
    @Public private static BlockFlower field_72097_ad;
    @Public private static Block field_72098_bB;
    @Public private static BlockPistonExtension field_72099_aa;
    @Public private static Block field_72100_bC;
    @Public private static Block field_72101_ab;
    @Public private static Block field_72102_bH;
    @Public private static Block field_72103_ag;
    @Public private static Block field_72104_bI;
    @Public private static Block field_72105_ah;
    @Public private static Block field_72106_bF;
    @Public private static BlockFlower field_72107_ae;
    @Public private static BlockCauldron field_72108_bG;
    @Public private static Block field_72109_af;
    @Public private static Block field_82510_ck;
    @Public private static Block field_82511_ci;
    @Public private static Block field_82512_cj;
    @Public private static Block field_82513_cg;
    @Public private static Block field_82514_ch;
    @Public private static Block field_82515_ce;
    @Public private static Block field_82516_cf;
    @Public private static Block field_82517_cc;
    @Public private static BlockBeacon field_82518_cd;
    @Public private static Block field_94337_cv;
    @Public private static Block field_94338_cu;
    @Public private static Block field_94339_ct;
    @Public private static BlockHopper field_94340_cs;
    @Public private static Block field_94341_cq;
    @Public private static Block field_94342_cr;
    @Public private static Block field_94343_co;
    @Public private static BlockDaylightDetector field_94344_cp;
    @Public private static Block field_94345_cm;
    @Public private static Block field_94346_cn;
    @Public private static Block field_94347_ck;
    @Public private static Block field_94348_cl;
    @Public private static Block field_96469_cy;
    @Public private static Block field_111031_cC;
    @Public private static Block field_111032_cD;
    @Public private static Block field_111034_cE;
    @Public private static Block field_111038_cB;
    @Public private static Block field_111039_cA;

    @Dynamic
    @Inject(method = "registerBlocks", at = @At("RETURN"))
    private static void registerBlocks(CallbackInfo ci) {
        field_71938_D = Blocks.lava;
        field_71939_E = Blocks.sand;
        field_71940_F = Blocks.gravel;
        field_71941_G = Blocks.gold_ore;
        field_71942_A = Blocks.flowing_water; //BlockFluid
        field_71943_B = Blocks.water;
        field_71944_C = Blocks.flowing_lava; //BlockFluid
        field_71945_L = Blocks.sponge;
        field_71946_M = Blocks.glass;
        field_71947_N = Blocks.lapis_ore;
        field_71948_O = Blocks.lapis_block;
        field_71950_I = Blocks.coal_ore;
        field_71952_K = Blocks.leaves;
        field_71954_T = Blocks.golden_rail;
        field_71955_W = Blocks.web;
        field_71956_V = Blocks.sticky_piston;
        field_71957_Q = Blocks.sandstone;
        field_71958_P = Blocks.dispenser;
        field_71959_S = Blocks.bed;
        field_71960_R = Blocks.noteblock;
        field_71961_Y = Blocks.deadbush;
        field_71962_X = Blocks.tallgrass;
        field_71963_Z = Blocks.piston;
        field_71979_v = Blocks.dirt;
        field_71980_u = Blocks.grass;
        field_71981_t = Blocks.stone;
        field_71986_z = Blocks.bedrock;
        field_71987_y = Blocks.sapling;
        field_71988_x = Blocks.planks;
        field_71991_bz = Blocks.waterlily;
        field_71992_bw = Blocks.brick_stairs;
        field_71993_bv = Blocks.fence_gate;
        field_71994_by = Blocks.mycelium;
        field_71995_bx = Blocks.stone_brick_stairs;
        field_71996_bs = Blocks.pumpkin_stem;
        field_71997_br = Blocks.melon_block;
        field_71998_bu = Blocks.vine;
        field_71999_bt = Blocks.melon_stem;
        field_72000_bn = Blocks.brown_mushroom_block;
        field_72001_bo = Blocks.red_mushroom_block;
        field_72002_bp = Blocks.iron_bars;
        field_72003_bq = Blocks.glass_pane;
        field_72004_bj = Blocks.stained_glass;
        field_72005_bk = Blocks.trapdoor;
        field_72006_bl = Blocks.monster_egg;
        field_72007_bm = Blocks.stonebrick;
        field_72008_bf = Blocks.lit_pumpkin;
        field_72009_bg = Blocks.cake;
        field_72010_bh = Blocks.unpowered_repeater;
        field_72011_bi = Blocks.powered_repeater;
        field_72012_bb = Blocks.netherrack;
        field_72013_bc = Blocks.soul_sand;
        field_72014_bd = Blocks.glowstone;
        field_72015_be = Blocks.portal;
        field_72031_aZ = Blocks.fence;
        field_72032_aY = Blocks.jukebox;
        field_72033_bA = Blocks.nether_brick;
        field_72034_aR = Blocks.stone_button;
        field_72035_aQ = Blocks.redstone_torch;
        field_72036_aT = Blocks.ice;
        field_72037_aS = Blocks.snow_layer;
        field_72038_aV = Blocks.cactus;
        field_72039_aU = Blocks.snow;
        field_72040_aX = Blocks.reeds;
        field_72041_aW = Blocks.clay;
        field_72042_aI = Blocks.wall_sign;
        field_72043_aJ = Blocks.lever;
        field_72044_aK = Blocks.stone_pressure_plate;
        field_72045_aL = Blocks.iron_door;
        field_72046_aM = Blocks.wooden_pressure_plate;
        field_72047_aN = Blocks.redstone_ore;
        field_72048_aO = Blocks.lit_redstone_ore;
        field_72049_aP = Blocks.unlit_redstone_torch;
        field_72051_aB = Blocks.furnace;
        field_72052_aC = Blocks.lit_furnace;
        field_72053_aD = Blocks.standing_sign;
        field_72054_aE = Blocks.wooden_door;
        field_72055_aF = Blocks.ladder;
        field_72056_aG = Blocks.rail;
        field_72057_aH = Blocks.stone_stairs;
        field_72058_az = Blocks.wheat;
        field_72060_ay = Blocks.crafting_table;
        field_72061_ba = Blocks.pumpkin;
        field_72062_bU = Blocks.tripwire;
        field_72063_at = Blocks.oak_stairs;
        field_72064_bT = Blocks.tripwire_hook;
        field_72065_as = Blocks.mob_spawner;
        field_72066_bS = Blocks.ender_chest;
        field_72067_ar = Blocks.fire;
        field_72068_bR = Blocks.emerald_ore;
        field_72070_bY = Blocks.jungle_stairs;
        field_72071_ax = Blocks.diamond_block;
        field_72072_bX = Blocks.birch_stairs;
        field_72073_aw = Blocks.diamond_ore;
        field_72074_bW = Blocks.spruce_stairs;
        field_72075_av = Blocks.redstone_wire;
        field_72076_bV = Blocks.emerald_block;
        field_72077_au = Blocks.chest;
        field_72078_bL = Blocks.redstone_lamp;
        field_72079_ak = Blocks.stone_slab;
        field_72080_bM = Blocks.lit_redstone_lamp;
        field_72081_al = Blocks.brick_block;
        field_72082_bJ = Blocks.end_stone;
        field_72083_ai = Blocks.iron_block;
        field_72084_bK = Blocks.dragon_egg;
        field_72085_aj = Blocks.double_stone_slab;
        field_72086_bP = Blocks.cocoa;
        field_72087_ao = Blocks.mossy_cobblestone;
        field_72088_bQ = Blocks.sandstone_stairs;
        field_72089_ap = Blocks.obsidian;
        field_72090_bN = Blocks.double_wooden_slab;
        field_72091_am = Blocks.tnt;
        field_72092_bO = Blocks.wooden_slab;
        field_72093_an = Blocks.bookshelf;
        field_72094_bD = Blocks.nether_wart;
        field_72095_ac = Blocks.piston_extension;
        field_72096_bE = Blocks.enchanting_table;
        field_72097_ad = Blocks.yellow_flower;
        field_72098_bB = Blocks.nether_brick_fence;
        field_72099_aa = Blocks.piston_head;
        field_72100_bC = Blocks.nether_brick_stairs;
        field_72101_ab = Blocks.wool;
        field_72102_bH = Blocks.end_portal;
        field_72103_ag = Blocks.red_mushroom;
        field_72104_bI = Blocks.end_portal_frame;
        field_72105_ah = Blocks.gold_block;
        field_72106_bF = Blocks.brewing_stand;
        field_72107_ae = Blocks.red_flower;
        field_72108_bG = Blocks.cauldron;
        field_72109_af = Blocks.brown_mushroom;
        field_82510_ck = Blocks.anvil;
        field_82511_ci = Blocks.wooden_button;
        field_82512_cj = Blocks.skull;
        field_82513_cg = Blocks.carrots;
        field_82514_ch = Blocks.potatoes;
        field_82515_ce = Blocks.cobblestone_wall;
        field_82516_cf = Blocks.flower_pot;
        field_82517_cc = Blocks.command_block;
        field_82518_cd = Blocks.beacon;
        field_94337_cv = Blocks.activator_rail;
        field_94338_cu = Blocks.quartz_stairs;
        field_94339_ct = Blocks.quartz_block;
        field_94340_cs = Blocks.hopper;
        field_94341_cq = Blocks.redstone_block;
        field_94342_cr = Blocks.quartz_ore;
        field_94343_co = Blocks.powered_comparator;
        field_94344_cp = Blocks.daylight_detector;
        field_94345_cm = Blocks.heavy_weighted_pressure_plate;
        field_94346_cn = Blocks.unpowered_comparator;
        field_94347_ck = Blocks.trapped_chest;
        field_94348_cl = Blocks.light_weighted_pressure_plate;
        field_96469_cy = Blocks.dropper;
        field_111031_cC = Blocks.carpet;
        field_111032_cD = Blocks.hardened_clay;
        field_111034_cE = Blocks.coal_block;
        field_111038_cB = Blocks.hay_block;
        field_111039_cA = Blocks.stained_hardened_clay;
    }
}
