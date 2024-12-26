package space.libs.mixins;

import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.RegistryNamespaced;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.libs.core.CompatLibCore;
import space.libs.util.cursedmixinextensions.annotations.NewConstructor;
import space.libs.util.cursedmixinextensions.annotations.Public;
import space.libs.util.cursedmixinextensions.annotations.ShadowConstructor;

@SuppressWarnings("all")
@Mixin(value = Item.class, priority = 200)
public abstract class MixinItem implements IItem {

    @Shadow
    public static @Final RegistryNamespaced itemRegistry;

    @Shadow
    public static Item getItemById(int p_150899_0_) {
        throw new AbstractMethodError();
    }

    @Shadow
    public abstract String getUnlocalizedName();

    @ShadowConstructor
    public void Item() {}

    @NewConstructor
    public void Item(int id) {
        Item();
        this.field_77779_bT = 256 + id;
        try {
            CompatLibCore.LOGGER.info("Old Register Item ID : " + this.field_77779_bT);
            CompatLibCore.LOGGER.info("Name : " + this.getUnlocalizedName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // TODO?
    }

    /** 1.6 itemID */
    public int field_77779_bT;

    public void setItemID(int id) {
        this.field_77779_bT = id;
    }

    /**
     * @author HowardZHY
     * @reason 1.6 item id
     */
    /*@Overwrite
    Public static int getIdFromItem(Item item) {
        if (item == null) {
            return 0;
        } else {
            IItem accessor = (IItem) item;
            int id = itemRegistry.getIDForObject(item);
            accessor.setItemID(id);
            return id;
        }
    }*/

    /** Legacy Item Fields */
    @Public private static Item field_77669_D;
    @Public private static Item field_77670_E;
    @Public private static Item field_77671_F;
    @Public private static Item field_77672_G;
    @Public private static Item field_77673_A;
    @Public private static Item field_77674_B;
    @Public private static Item field_77675_C;
    @Public private static Item field_77676_L;
    @Public private static Item field_77677_M;
    @Public private static Item field_77678_N;
    @Public private static Item field_77679_O;
    @Public private static Item field_77680_H;
    @Public private static Item field_77681_I;
    @Public private static Item field_77682_J;
    @Public private static Item field_77683_K;
    @Public private static Item field_77684_U;
    @Public private static Item field_77685_T;
    @Public private static ItemArmor field_77686_W;
    @Public private static ItemArmor field_77687_V;
    @Public private static Item field_77688_Q;
    @Public private static Item field_77689_P;
    @Public private static Item field_77690_S;
    @Public private static Item field_77691_R;
    @Public private static ItemArmor field_77692_Y;
    @Public private static ItemArmor field_77693_X;
    @Public private static ItemArmor field_77694_Z;
    @Public private static Item field_77695_f;
    @Public private static Item field_77696_g;
    @Public private static Item field_77702_n;
    @Public private static Item field_77703_o;
    @Public private static Item field_77704_l;
    @Public private static Item field_77705_m;
    @Public private static Item field_77706_j;
    @Public private static ItemBow field_77707_k;
    @Public private static Item field_77708_h;
    @Public private static Item field_77709_i;
    @Public private static Item field_77710_w;
    @Public private static Item field_77711_v;
    @Public private static Item field_77712_u;
    @Public private static Item field_77713_t;
    @Public private static Item field_77714_s;
    @Public private static Item field_77715_r;
    @Public private static Item field_77716_q;
    @Public private static Item field_77717_p;
    @Public private static Item field_77718_z;
    @Public private static Item field_77719_y;
    @Public private static Item field_77720_x;
    @Public private static Item field_77721_bz;
    @Public private static Item field_77722_bw;
    @Public private static Item field_77723_bv;
    @Public private static Item field_77724_by;
    @Public private static Item field_77725_bx;
    @Public private static ItemPotion field_77726_bs;
    @Public private static Item field_77727_br;
    @Public private static Item field_77728_bu;
    @Public private static Item field_77729_bt;
    @Public private static Item field_77730_bn;
    @Public private static Item field_77731_bo;
    @Public private static Item field_77732_bp;
    @Public private static Item field_77733_bq;
    @Public private static Item field_77734_bj;
    @Public private static Item field_77735_bk;
    @Public private static Item field_77736_bl;
    @Public private static Item field_77737_bm;
    @Public private static Item field_77738_bf;
    @Public private static Item field_77739_bg;
    @Public private static Item field_77740_bh;
    @Public private static Item field_77741_bi;
    @Public private static Item field_77742_bb;
    @Public private static Item field_77743_bc;
    @Public private static ItemMap field_77744_bd;
    @Public private static ItemShears field_77745_be;
    @Public private static Item field_77746_aZ;
    @Public private static Item field_77747_aY;
    @Public private static Item field_77748_bA;
    @Public private static ItemFishingRod field_77749_aR;
    @Public private static Item field_77750_aQ;
    @Public private static Item field_77751_aT;
    @Public private static Item field_77752_aS;
    @Public private static Item field_77753_aV;
    @Public private static Item field_77754_aU;
    @Public private static Item field_77755_aX;
    @Public private static Item field_77756_aW;
    @Public private static Item field_77757_aI;
    @Public private static Item field_77758_aJ;
    @Public private static Item field_77759_aK;
    @Public private static Item field_77760_aL;
    @Public private static Item field_77761_aM;
    @Public private static Item field_77762_aN;
    @Public private static Item field_77763_aO;
    @Public private static Item field_77764_aP;
    @Public private static Item field_77765_aA;
    @Public private static Item field_77766_aB;
    @Public private static Item field_77767_aC;
    @Public private static Item field_77768_aD;
    @Public private static Item field_77769_aE;
    @Public private static Item field_77770_aF;
    @Public private static Item field_77771_aG;
    @Public private static Item field_77772_aH;
    @Public private static Item field_77773_az;
    @Public private static Item field_77775_ay;
    @Public private static Item field_77776_ba;
    @Public private static Item field_77778_at;
    @Public private static Item field_77780_as;
    @Public private static Item field_77781_bS;
    @Public private static Item field_77782_ar;
    @Public private static Item field_77783_bR;
    @Public private static Item field_77784_aq;
    @Public private static Item field_77786_ax;
    @Public private static Item field_77788_aw;
    @Public private static Item field_77790_av;
    @Public private static Item field_77792_au;
    @Public private static Item field_77793_bL;
    @Public private static ItemArmor field_77794_ak;
    @Public private static Item field_77795_bM;
    @Public private static ItemArmor field_77796_al;
    @Public private static Item field_77797_bJ;
    @Public private static ItemArmor field_77798_ai;
    @Public private static Item field_77799_bK;
    @Public private static ItemArmor field_77800_aj;
    @Public private static Item field_77801_bP;
    @Public private static ItemArmor field_77802_ao;
    @Public private static Item field_77803_bQ;
    @Public private static Item field_77804_ap;
    @Public private static Item field_77805_bN;
    @Public private static ItemArmor field_77806_am;
    @Public private static Item field_77807_bO;
    @Public private static ItemArmor field_77808_an;
    @Public private static Item field_77809_bD;
    @Public private static ItemArmor field_77810_ac;
    @Public private static Item field_77811_bE;
    @Public private static ItemArmor field_77812_ad;
    @Public private static Item field_77813_bB;
    @Public private static ItemArmor field_77814_aa;
    @Public private static Item field_77815_bC;
    @Public private static ItemArmor field_77816_ab;
    @Public private static Item field_77817_bH;
    @Public private static ItemArmor field_77818_ag;
    @Public private static Item field_77819_bI;
    @Public private static ItemArmor field_77820_ah;
    @Public private static Item field_77821_bF;
    @Public private static ItemArmor field_77822_ae;
    @Public private static Item field_77823_bG;
    @Public private static ItemArmor field_77824_af;
    @Public private static Item field_82791_bT;
    @Public private static Item field_82792_bS;
    @Public private static Item field_82793_bR;
    @Public private static Item field_82794_bL;
    @Public private static Item field_82795_bM;
    @Public private static Item field_82796_bJ;
    @Public private static Item field_82797_bK;
    @Public private static Item field_82798_bP;
    @Public private static Item field_82799_bQ;
    @Public private static Item field_82800_bN;
    @Public private static ItemEmptyMap field_82801_bO;
    @Public private static Item field_82802_bI;
    @Public private static Item field_85180_cf;
    @Public private static Item field_92104_bU;
    @Public private static ItemEnchantedBook field_92105_bW;
    @Public private static Item field_92106_bV;
    @Public private static Item field_94582_cb;
    @Public private static Item field_94583_ca;
    @Public private static Item field_94584_bZ;
    @Public private static Item field_94585_bY;
    @Public private static Item field_96600_cc;
    @Public private static Item field_111212_ci;
    @Public private static Item field_111213_cg;
    @Public private static Item field_111214_ch;
    @Public private static Item field_111215_ce;
    @Public private static Item field_111216_cf;

    @Inject(method = "registerItems", at = @At("RETURN"))
    private static void registerItems(CallbackInfo ci) {
        field_77669_D = Items.stick;
        field_77670_E = Items.bowl;
        field_77671_F = Items.mushroom_stew;
        field_77672_G = Items.golden_sword;
        field_77673_A = Items.diamond_shovel;
        field_77674_B = Items.diamond_pickaxe;
        field_77675_C = Items.diamond_axe;
        field_77676_L = Items.feather;
        field_77677_M = Items.gunpowder;
        field_77678_N = Items.wooden_hoe;
        field_77679_O = Items.stone_hoe;
        field_77680_H = Items.golden_shovel;
        field_77681_I = Items.golden_pickaxe;
        field_77682_J = Items.golden_axe;
        field_77683_K = Items.string;
        field_77684_U = Items.bread;
        field_77685_T = Items.wheat;
        field_77686_W = Items.leather_chestplate;
        field_77687_V = Items.leather_helmet;
        field_77688_Q = Items.diamond_hoe;
        field_77689_P = Items.iron_hoe;
        field_77690_S = Items.wheat_seeds;
        field_77691_R = Items.golden_hoe;
        field_77692_Y = Items.leather_boots;
        field_77693_X = Items.leather_leggings;
        field_77694_Z = Items.chainmail_helmet;
        field_77695_f = Items.iron_shovel;
        field_77696_g = Items.iron_pickaxe;
        field_77702_n = Items.diamond;
        field_77703_o = Items.iron_ingot;
        field_77704_l = Items.arrow;
        field_77705_m = Items.coal;
        field_77706_j = Items.apple;
        field_77707_k = Items.bow;
        field_77708_h = Items.iron_axe;
        field_77709_i = Items.flint_and_steel;
        field_77710_w = Items.stone_shovel;
        field_77711_v = Items.stone_sword;
        field_77712_u = Items.wooden_axe;
        field_77713_t = Items.wooden_pickaxe;
        field_77714_s = Items.wooden_shovel;
        field_77715_r = Items.wooden_sword;
        field_77716_q = Items.iron_sword;
        field_77717_p = Items.gold_ingot;
        field_77718_z = Items.diamond_sword;
        field_77719_y = Items.stone_axe;
        field_77720_x = Items.stone_pickaxe;
        field_77721_bz = Items.cauldron;
        field_77722_bw = Items.blaze_powder;
        field_77723_bv = Items.fermented_spider_eye;
        field_77724_by = Items.brewing_stand;
        field_77725_bx = Items.magma_cream;
        field_77726_bs = Items.potionitem;
        field_77727_br = Items.nether_wart;
        field_77728_bu = Items.spider_eye;
        field_77729_bt = Items.glass_bottle;
        field_77730_bn = Items.ender_pearl;
        field_77731_bo = Items.blaze_rod;
        field_77732_bp = Items.ghast_tear;
        field_77733_bq = Items.gold_nugget;
        field_77734_bj = Items.cooked_beef;
        field_77735_bk = Items.chicken;
        field_77736_bl = Items.cooked_chicken;
        field_77737_bm = Items.rotten_flesh;
        field_77738_bf = Items.melon;
        field_77739_bg = Items.pumpkin_seeds;
        field_77740_bh = Items.melon_seeds;
        field_77741_bi = Items.beef;
        field_77742_bb = Items.repeater;
        field_77743_bc = Items.cookie;
        field_77744_bd = Items.filled_map;
        field_77745_be = Items.shears;
        field_77746_aZ = Items.cake;
        field_77747_aY = Items.sugar;
        field_77748_bA = Items.ender_eye;
        field_77749_aR = Items.fishing_rod;
        field_77750_aQ = Items.compass;
        field_77751_aT = Items.glowstone_dust;
        field_77752_aS = Items.clock;
        field_77753_aV = Items.cooked_fished;
        field_77754_aU = Items.fish;
        field_77755_aX = Items.bone;
        field_77756_aW = Items.dye;
        field_77757_aI = Items.clay_ball;
        field_77758_aJ = Items.reeds;
        field_77759_aK = Items.paper;
        field_77760_aL = Items.book;
        field_77761_aM = Items.slime_ball;
        field_77762_aN = Items.chest_minecart;
        field_77763_aO = Items.furnace_minecart;
        field_77764_aP = Items.egg;
        field_77765_aA = Items.saddle;
        field_77766_aB = Items.iron_door;
        field_77767_aC = Items.redstone;
        field_77768_aD = Items.snowball;
        field_77769_aE = Items.boat;
        field_77770_aF = Items.leather;
        field_77771_aG = Items.milk_bucket;
        field_77772_aH = Items.brick;
        field_77773_az = Items.minecart;
        field_77775_ay = Items.lava_bucket;
        field_77776_ba = Items.bed;
        field_77778_at = Items.golden_apple;
        field_77780_as = Items.painting;
        field_77781_bS = Items.record_11;
        field_77782_ar = Items.cooked_porkchop;
        field_77783_bR = Items.record_ward;
        field_77784_aq = Items.porkchop;
        field_77786_ax = Items.water_bucket;
        field_77788_aw = Items.bucket;
        field_77790_av = Items.wooden_door;
        field_77792_au = Items.sign;
        field_77793_bL = Items.record_chirp;
        field_77794_ak = Items.diamond_boots;
        field_77795_bM = Items.record_far;
        field_77796_al = Items.golden_helmet;
        field_77797_bJ = Items.record_cat;
        field_77798_ai = Items.diamond_chestplate;
        field_77799_bK = Items.record_blocks;
        field_77800_aj = Items.diamond_leggings;
        field_77801_bP = Items.record_stal;
        field_77802_ao = Items.golden_boots;
        field_77803_bQ = Items.record_strad;
        field_77804_ap = Items.flint;
        field_77805_bN = Items.record_mall;
        field_77806_am = Items.golden_chestplate;
        field_77807_bO = Items.record_mellohi;
        field_77808_an = Items.golden_leggings;
        field_77809_bD = Items.experience_bottle;
        field_77810_ac = Items.chainmail_boots;
        field_77811_bE = Items.fire_charge;
        field_77812_ad = Items.iron_helmet;
        field_77813_bB = Items.speckled_melon;
        field_77814_aa = Items.chainmail_chestplate;
        field_77815_bC = Items.spawn_egg;
        field_77816_ab = Items.chainmail_leggings;
        field_77817_bH = Items.emerald;
        field_77818_ag = Items.iron_boots;
        field_77819_bI = Items.record_13;
        field_77820_ah = Items.diamond_helmet;
        field_77821_bF = Items.writable_book;
        field_77822_ae = Items.iron_chestplate;
        field_77823_bG = Items.written_book;
        field_77824_af = Items.iron_leggings;
        field_82791_bT = Items.pumpkin_pie;
        field_82792_bS = Items.nether_star;
        field_82793_bR = Items.carrot_on_a_stick;
        field_82794_bL = Items.potato;
        field_82795_bM = Items.baked_potato;
        field_82796_bJ = Items.flower_pot;
        field_82797_bK = Items.carrot;
        field_82798_bP = Items.golden_carrot;
        field_82799_bQ = Items.skull;
        field_82800_bN = Items.poisonous_potato;
        field_82801_bO = Items.map;
        field_82802_bI = Items.item_frame;
        field_85180_cf = Items.record_wait;
        field_92104_bU = Items.fireworks;
        field_92105_bW = Items.enchanted_book;
        field_92106_bV = Items.firework_charge;
        field_94582_cb = Items.tnt_minecart;
        field_94583_ca = Items.quartz;
        field_94584_bZ = Items.netherbrick;
        field_94585_bY = Items.comparator;
        field_96600_cc = Items.hopper_minecart;
        field_111212_ci = Items.name_tag;
        field_111213_cg = Items.diamond_horse_armor;
        field_111214_ch = Items.lead;
        field_111215_ce = Items.iron_horse_armor;
        field_111216_cf = Items.golden_horse_armor;
    }
}
