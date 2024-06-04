package space.libs.mixins.client;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.util.cursedmixinextensions.annotations.Public;

import static net.minecraft.client.entity.AbstractClientPlayer.getLocationSkin;

@SuppressWarnings("all")
@Mixin(value = AbstractClientPlayer.class, priority = 200)
public abstract class MixinAbstractClientPlayer extends EntityPlayer {

    public MixinAbstractClientPlayer(World p_i45324_1_, GameProfile p_i45324_2_) {
        super(p_i45324_1_, p_i45324_2_);
    }

    @Shadow
    private ResourceLocation locationSkin;

    @Shadow
    private ResourceLocation locationCape;

    @Shadow
    public ResourceLocation getLocationCape() {
        return this.locationCape;
    }

    @Shadow
    public static ThreadDownloadImageData getDownloadImageSkin(ResourceLocation resourceLocationIn, String username) {
        throw new AbstractMethodError();
    }

    public ThreadDownloadImageData field_110316_a;

    public ThreadDownloadImageData field_110315_c;

    /** getLocationCape */
    @Public
    private static ResourceLocation func_110299_g(String paramString) {
        return new ResourceLocation("cloaks/" + StringUtils.stripControlCodes(paramString));
    }

    /** getSkinUrl */
    @Public
    private static String func_110300_d(String name) {
        return String.format("https://mineskin.eu/skin/%s.png", new Object[] {StringUtils.stripControlCodes(name)});
    } //mirror url

    /** getDownloadImage */
    @Public
    private static ThreadDownloadImageData func_110301_a(ResourceLocation location, String name, ResourceLocation paramResourceLocation2, IImageBuffer buffer) {
        return getDownloadImageSkin(location, name);
    }

    /** setupCustomSkin */
    public void func_110302_j() {
        String str = getCommandSenderName();
        if (str.isEmpty())
            return;
        this.locationSkin = getLocationSkin(str);
        this.locationCape = func_110299_g(str);
        this.field_110316_a = getDownloadImageSkin(this.locationSkin, str);
        this.field_110315_c = func_110307_b(this.locationCape, str);
    }

    /** getLocationSkull */
    @Public
    private static ResourceLocation func_110305_h(String paramString) {
        return new ResourceLocation("skull/" + StringUtils.stripControlCodes(paramString));
    }

    /** getDownloadImageCape */
    @Public
    private static ThreadDownloadImageData func_110307_b(ResourceLocation location, String name) {
        //return func_110301_a(location, func_110308_e(name), null, null);
        throw new UnsupportedOperationException("Cannot support old capes");
    }

    /** getCapeUrl */
    @Public
    private static String func_110308_e(String name) {
        return String.format("http://skins.minecraft.net/MinecraftCloaks/%s.png", new Object[] {StringUtils.stripControlCodes(name)});
    } // won't work anymore

    /** getTextureSkin */
    public ThreadDownloadImageData func_110309_l() {
        return this.field_110316_a;
    }

    /** getTextureSkin */
    public ThreadDownloadImageData func_110310_o() {
        return this.field_110315_c;
    }

}
