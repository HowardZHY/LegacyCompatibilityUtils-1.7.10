package space.libs.mixins.block;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import space.libs.core.ICoreUtils;
import space.libs.fml.BlockProxy;
import space.libs.util.MappedName;
import space.libs.util.cursedmixinextensions.annotations.*;
import space.libs.util.forge.RegistryUtils;

@SuppressWarnings("unused")
@Mixin(value = Block.class, priority = 20)
public abstract class MixinBlock implements BlockProxy, IBlock, ICoreUtils {

    @Shadow
    protected String textureName;

    @Shadow
    private String unlocalizedName;

    @Shadow
    public abstract Block setStepSound(Block.SoundType sound);

    @Shadow
    public abstract Block setTickRandomly(boolean shouldTick);

    @Shadow
    public @Final void setBlockBounds(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {}

    @Shadow
    public abstract boolean isOpaqueCube();

    @Shadow
    public abstract int damageDropped(int meta);

    @Shadow
    public abstract Block setCreativeTab(CreativeTabs tab);

    @ShadowConstructor
    protected void Block(Material materialIn) {}

    @NewConstructor
    public void Block(int id, Material material) {
        Block(material);
        IBlock.InitLegacyBlock(GetBlockInstance(), id, "Block");
        //TODO?
    }

    @Inject(
        method = "<init>(Lnet/minecraft/block/material/Material;)V",
        at = @At(
            value = "FIELD",
            opcode = Opcodes.PUTFIELD,
            target = "Lnet/minecraft/block/Block;blockMaterial:Lnet/minecraft/block/material/Material;",
            shift = At.Shift.AFTER
        )
    )
    protected void init(Material materialIn, CallbackInfo ci) {
        this.field_72018_cp = materialIn;
    }

    @Inject(method = "setUnlocalizedName", at = @At("RETURN"))
    public void setUnlocalizedName(String name, CallbackInfoReturnable<Block> cir) {
        this.field_71968_b = name;
        if (this.LegacyBlockNoID) {
            GameRegistry.registerBlock(GetBlockInstance(), name);
        } else if (this.LegacyBlock) {
            LOGGER.info("Name : " + this.RawUnlocalizedName());
            RegistryUtils.registerLegacyBlock(GetBlockInstance(), name);
        }
        this.func_71928_r_();
    }

    public boolean LegacyBlock;

    public boolean LegacyBlockNoID;

    @Override
    public boolean IsLegacyBlock() {
        return (LegacyBlock || LegacyBlockNoID);
    }

    @Override
    public boolean IsLegacyBlockNoID() {
        return LegacyBlockNoID;
    }

    public Block GetBlockInstance() {
        return (Block) (Object) this;
    }

    public Block[] GetBlocksList() {
        return field_71973_m;
    }

    public Block GetBlockInList(int id) {
        return field_71973_m[id];
    }

    public void SetBlockInList(int id) {
        field_71973_m[id] = this.GetBlockInstance();
    }

    public boolean[] GetOpaqueCubeList() {
        return field_71970_n;
    }

    public int[] GetLightOpacityList() {
        return field_71971_o;
    }

    public boolean[] GetCanBlocksLightList() {
        return field_71985_p;
    }

    public int[] GetLightValueList() {
        return field_71984_q;
    }

    public boolean[] GetUseNearLightList() {
        return field_71982_s;
    }

    @Override
    public void SetLegacyBlock(int id, String type) {
        this.LegacyBlock = true;
        if (field_71973_m[id] != null) {
            if (RegistryUtils.is17Block(id)) {
                this.SetLegacyID(id + 1000);
            } else {
                throw new IllegalArgumentException("Block ID " + id + " is already occupied by " + field_71973_m[id] + " when adding " + this);
            }
        } else {
            this.SetLegacyID(id);
            LOGGER.info("Legacy Register " + type + " ID : " + this.field_71990_ca);
        }
    }

    @Override
    public void SetLegacyBlockNoID(String type) {
        this.LegacyBlockNoID = true;
        LOGGER.warn("Legacy Register " + type + " Has Invalid ID : " + this.getClass());
    }

    @Override
    public int GetLegacyID() {
        return this.field_71990_ca;
    }

    @Override
    public void SetLegacyID(int id) {
        field_71973_m[id] = this.GetBlockInstance();
        this.field_71990_ca = id;
        field_71970_n[id] = this.isOpaqueCube();
        field_71971_o[id] = this.isOpaqueCube() ? 255 : 0;
        field_71985_p[id] = !this.field_72018_cp.blocksLight();
    }

    @Override
    public String RawUnlocalizedName() {
        return unlocalizedName;
    }

    /** Legacy Fields */
    @Public private static int[] blockFireSpreadSpeed = new int[4096];

    @Public private static int[] blockFlammability = new int[4096];

    @MappedName(value = "blocksList", until = "1.6.4")
    @Public private static Block[] field_71973_m = new Block[4096];

    @MappedName(value = "opaqueCubeLookup", until = "1.6.4")
    @Public private static boolean[] field_71970_n = new boolean[4096];

    @MappedName(value = "lightOpacity", until = "1.6.4")
    @Public private static int[] field_71971_o = new int[4096];

    @MappedName(value = "canBlockGrass", until = "1.6.4")
    @Public private static boolean[] field_71985_p = new boolean[4096];

    @MappedName(value = "lightValue", until = "1.6.4")
    @Public private static int[] field_71984_q = new int[4096];

    @MappedName(value = "useNeighborBrightness", until = "1.6.4")
    @Public private static boolean[] field_71982_s = new boolean[4096];

    @MappedName(value = "blockID", until = "1.6.4")
    public int field_71990_ca;

    @MappedName(value = "stepSound", until = "1.6.4")
    public StepSound field_72020_cn;

    @MappedName(value = "blockMaterial", until = "1.6.4")
    public Material field_72018_cp;

    @MappedName(value = "unlocalizedName", until = "1.6.4")
    public String field_71968_b;

    /** Legacy Methods */
    @MappedName(value = "initializeBlock", until = "1.6.4")
    public void func_71928_r_() {}

    public Block func_71884_a(StepSound stepSound) {
        this.field_72020_cn = stepSound;
        return this.setStepSound(stepSound);
    }

    @MappedName(value = "getTextureName", until = "1.6.4")
    public String func_111023_E() {
        return this.textureName == null ? "MISSING_ICON_BLOCK_" + Block.getIdFromBlock(this.GetBlockInstance()) + "_" + this.unlocalizedName : this.textureName;
    }
}
