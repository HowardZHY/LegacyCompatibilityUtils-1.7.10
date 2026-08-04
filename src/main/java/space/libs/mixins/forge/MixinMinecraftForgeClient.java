package space.libs.mixins.forge;

import net.minecraft.item.Item;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import space.libs.util.cursedmixinextensions.annotations.Public;

@Mixin(value = MinecraftForgeClient.class, remap = false)
public class MixinMinecraftForgeClient {

    @SuppressWarnings("MismatchedReadAndWriteOfArray")
    @Public
    private static IItemRenderer[] customItemRenderers = new IItemRenderer[32767];

    @Shadow
    public static void registerItemRenderer(Item item, IItemRenderer renderer) {}

    @Public
    private static void registerItemRenderer(int itemID, IItemRenderer renderer) {
        registerItemRenderer(Item.getItemById(itemID), renderer);
        customItemRenderers[itemID] = renderer;
    }
}
