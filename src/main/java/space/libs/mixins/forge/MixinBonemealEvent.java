package space.libs.mixins.forge;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.BonemealEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.libs.util.cursedmixinextensions.annotations.*;

@SuppressWarnings("unused")
@Mixin(value = BonemealEvent.class, remap = false)
public class MixinBonemealEvent {

    public int ID;

    public int X, Y, Z;

    @ShadowConstructor
    public void BonemealEvent(EntityPlayer player, World world, Block block, int x, int y, int z) {}

    @NewConstructor
    public void BonemealEvent(EntityPlayer player, World world, int id, int x, int y, int z) {
        BonemealEvent(player, world, Block.getBlockById(id), x, y, z);
    }

    @Inject(method = "<init>(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/block/Block;III)V", at = @At("RETURN"))
    public void init(EntityPlayer player, World world, Block block, int x, int y, int z, CallbackInfo ci) {
        this.ID = Block.getIdFromBlock(block);
        this.X = x;
        this.Y = y;
        this.Z = z;
    }
}
