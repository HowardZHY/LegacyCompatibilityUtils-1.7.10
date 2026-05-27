package space.libs.mixins.forge;

import com.google.common.base.Throwables;
import com.google.common.collect.MapDifference;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.modloader.ModLoaderClientHelper;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.registry.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.resources.*;
import net.minecraft.entity.*;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.*;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.libs.fml.ItemData;
import space.libs.fml.client.KeyBindingRegistry;
import space.libs.fml.network.*;
import space.libs.interfaces.*;

import java.util.List;

@SideOnly(Side.CLIENT)
@Mixin(value = FMLClientHandler.class, remap = false)
public class MixinFMLClientHandler implements IIFMLSidedHandler {

    @Shadow
    private Minecraft client;

    @Dynamic
    @Inject(
        method = "beginMinecraftLoading",
        at = @At(
            value = "INVOKE",
            target = "Lcpw/mods/fml/common/FMLCommonHandler;beginLoading(Lcpw/mods/fml/common/IFMLSidedHandler;)V",
            shift = At.Shift.AFTER
        )
    )
    public void beginMinecraftLoading(Minecraft minecraft, List<IResourcePack> resourcePackList, IReloadableResourceManager resourceManager, CallbackInfo co) {
        ModLoaderClientHelper.INSTANCE = new ModLoaderClientHelper(minecraft);
    }

    @Dynamic
    @Inject(method = "finishMinecraftLoading", at = @At("TAIL"))
    public void finishMinecraftLoading(CallbackInfo ci) {
        KeyBindingRegistry.instance().uploadKeyBindingsToGame(client.gameSettings);
    }

    @Override
    public Entity spawnEntityIntoClientWorld(EntityRegistry.EntityRegistration er, EntitySpawnPacket packet) {
        WorldClient wc = client.theWorld;
        Class<? extends Entity> cls = er.getEntityClass();
        try {
            IEntitySpawnMessage accessor = (IEntitySpawnMessage) packet;
            Entity entity;
            int entityID = accessor.getEntityId();
            if (er.hasCustomSpawning()) {
                entity = er.doCustomSpawning(packet);
            } else {
                entity = cls.getConstructor(World.class).newInstance(wc);
                int offset = entityID - entity.getEntityId();
                entity.setEntityId(entityID);
                entity.setLocationAndAngles(accessor.getScaledX(), accessor.getScaledY(), accessor.getScaledZ(), accessor.getScaledYaw(), accessor.getScaledPitch());
                if (entity instanceof EntityLiving) {
                    ((EntityLiving)entity).rotationYawHead = accessor.getScaledHeadYaw();
                }
                Entity[] parts = entity.getParts();
                if (parts != null) {
                    for (Entity part : parts) {
                        part.setEntityId(part.getEntityId() + offset);
                    }
                }
            }
            entity.serverPosX = accessor.getRawX();
            entity.serverPosY = accessor.getRawY();
            entity.serverPosZ = accessor.getRawZ();
            if (entity instanceof IThrowableEntity) {
                EntityClientPlayerMP player = client.thePlayer;
                Entity thrower = player.getEntityId() == accessor.getThrowerId() ? player : wc.getEntityByID(accessor.getThrowerId());
                ((IThrowableEntity)entity).setThrower(thrower);
            }
            if (accessor.getDataWatcherList() != null) {
                entity.getDataWatcher().updateWatchedObjectsFromList(accessor.getDataWatcherList());
            }
            if (accessor.getThrowerId() > 0) {
                entity.setVelocity(accessor.getSpeedScaledX(), accessor.getSpeedScaledY(), accessor.getSpeedScaledZ());
            }
            if (entity instanceof IEntityAdditionalSpawnData) {
                ((IEntityAdditionalSpawnData)entity).readSpawnData(accessor.getDataStream());
            }
            wc.addEntityToWorld(entityID, entity);
            return entity;
        } catch (Exception e) {
            FMLLog.severe( "A severe problem occurred during the spawning of an entity", e);
            throw Throwables.propagate(e);
        }
    }

    @Override
    public void adjustEntityLocationOnClient(EntitySpawnAdjustmentPacket packet) {
        IEntityAdjustMessage accessor = (IEntityAdjustMessage) packet;
        Entity e = client.theWorld.getEntityByID(accessor.getEntityId());
        if (e != null) {
            e.serverPosX = accessor.getServerX();
            e.serverPosY = accessor.getServerY();
            e.serverPosZ = accessor.getServerZ();
        } else {
            FMLLog.fine("Attempted to adjust the position of entity %d which is not present on the client", accessor.getEntityId());
        }
    }

    @Override
    public void sendPacket(Packet packet) {
        EntityClientPlayerMP player = client.thePlayer;
        if (player != null) {
            player.sendQueue.addToSendQueue(packet);
        }
    }

    @Override
    public void displayMissingMods(ModMissingPacket modMissingPacket) {
        // INVALID
    }

    @Override
    public void handleTinyPacket(NetHandler handler, Packet131MapData mapData) {
        ((NetClientHandler)handler).fmlPacket131Callback(mapData);
    }

    @Override
    public void setClientCompatibilityLevel(byte level) {
        NetClientHandler.setConnectionCompatibilityLevel(level);
    }

    @Override
    public byte getClientCompatibilityLevel() {
        return NetClientHandler.getConnectionCompatibilityLevel();
    }

    @Override
    public void disconnectIDMismatch(MapDifference<Integer, ItemData> mapDifference, NetHandler netHandler, INetworkManager iNetworkManager) {
        // INVALID
    }

    @Override
    public void updateResourcePackList() {
        client.refreshResources();
    }
}
