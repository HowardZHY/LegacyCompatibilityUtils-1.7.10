package space.libs.mixins.forge;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.util.cursedmixinextensions.annotations.Public;

@SideOnly(Side.CLIENT)
@Mixin(value = ForgeHooksClient.class, remap = false)
public abstract class MixinForgeHooksClient {

    @Shadow
    public static String getArmorTexture(Entity entity, ItemStack armor, String def, int slot, String type) {
        throw new AbstractMethodError();
    }

    @Shadow
    public static int getSkyBlendColour(World world, int playerX, int playerY, int playerZ) {
        throw new AbstractMethodError();
    }

    @Public
    private static String getArmorTexture(Entity entity, ItemStack armor, String def, int slot, int layer, String type) {
        return getArmorTexture(entity, armor, def, slot, type);
    }

    @Public
    private static int getSkyBlendColour(World world, int playerX, int playerZ) {
        return getSkyBlendColour(world, playerX, 63, playerZ); // Incomplete
    }
}
