package space.libs.mixins;

import com.mojang.authlib.GameProfile;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.*;
import net.minecraft.stats.StatisticsFile;
import net.minecraft.world.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.libs.CompatLib;
import space.libs.fml.network.FMLNetworkHandler;
import space.libs.util.MappedName;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@Mixin(ServerConfigurationManager.class)
public abstract class MixinServerConfigurationManager {

    @Shadow
    private @Final MinecraftServer mcServer;

    @Shadow
    public @Final List<EntityPlayerMP> playerEntityList;

    @Shadow
    private @Final BanList bannedIPs;

    @Shadow
    private @Final UserListOps ops;

    @Shadow
    private @Final UserListWhitelist whiteListedPlayers;

    @Shadow
    private WorldSettings.GameType gameType;

    /** func_152596_g */
    @Shadow
    public abstract boolean canSendCommands(GameProfile profile);

    /** func_152597_c */
    @Shadow
    public void removePlayerFromWhitelist(GameProfile profile) {}

    /** func_152601_d */
    @Shadow
    public void addWhitelistedPlayer(GameProfile profile) {}

    /** func_156202_a */
    @Shadow
    public abstract StatisticsFile getPlayerStatsFile(EntityPlayer player);

    /** func_156205_a */
    @Shadow
    public void addOp(GameProfile profile) {}

    /** func_156207_e */
    @Shadow
    public abstract boolean canJoin(GameProfile profile);

    /** func_152610_b */
    @Shadow
    public void removeOp(GameProfile profile) {}

    @MappedName("isPlayerOpped")
    public boolean func_72353_e(String name) {
        GameProfile profile = this.getProfileFromName(name);
        if (profile != null) {
            return this.canSendCommands(profile);
        } else {
            return false;
        }
    }

    @MappedName("setGameType")
    public void func_72357_a(WorldSettings.GameType type) {
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            ServerConfigurationManager This = (ServerConfigurationManager) (Object) this;
            This.func_152604_a(type);
        } else {
            this.gameType = type;
        }
    }

    @MappedName("addToWhiteList")
    public void func_72359_h(String name) {
        GameProfile profile = this.getProfileFromName(name);
        if (profile != null) {
            this.addWhitelistedPlayer(profile);
        }
    }

    @MappedName("removeOP")
    public void func_72360_c(String name) {
        GameProfile profile = this.ops.getGameProfileFromName(name);
        if (profile != null) {
            this.removeOp(profile);
        }
    }

    @MappedName("getPlayerForUsername")
    public EntityPlayerMP func_72361_f(String name) {
        EntityPlayerMP entityplayermp;
        Iterator<EntityPlayerMP> iterator = this.playerEntityList.iterator();
        do {
            if (!iterator.hasNext())
                return null;
            entityplayermp = iterator.next();
        } while (!entityplayermp.getCommandSenderName().equalsIgnoreCase(name));
        return entityplayermp;
    }

    @MappedName("isAllowedToLogin")
    public boolean func_72370_d(String name) {
        try {
            return this.canJoin(this.getProfileFromName(name));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @MappedName("getOps")
    public Set<String> func_72376_i() {
        try {
            throw new UnsupportedOperationException("Server");
        } catch (Exception e) {
            CompatLib.LOGGER.warn("Getting Legacy OPList.");
            e.printStackTrace();
        }
        return Arrays.stream(this.ops.getKeys()).collect(Collectors.toSet());
    }

    public void func_72379_i(String name) {
        GameProfile profile = this.whiteListedPlayers.func_152706_a(name);
        if (profile != null) {
            this.removePlayerFromWhitelist(profile);
        }
    }

    @MappedName("addOp")
    public void func_72386_b(String name) {
        GameProfile profile = this.getProfileFromName(name);
        if (profile != null) {
            this.addOp(profile);
        }
    }

    @MappedName("getWhiteListedPlayers")
    public Set<String> func_72388_h() {
        try {
            throw new UnsupportedOperationException("Server");
        } catch (Exception e) {
            CompatLib.LOGGER.warn("Getting Legacy WhiteList.");
            e.printStackTrace();
        }
        return Arrays.stream(this.whiteListedPlayers.getKeys()).collect(Collectors.toSet());
    }

    @MappedName("getBannedPlayers")
    public BanList func_72390_e() {
        try {
            throw new UnsupportedOperationException();
        } catch (Exception e) {
            CompatLib.LOGGER.error("Legacy BanList is unsupported.");
            e.printStackTrace();
        }
        return this.bannedIPs;
    }

    @MappedName("getPlayerListAsString")
    public String func_72398_c() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < this.playerEntityList.size(); i++) {
            if (i > 0)
                s.append(", ");
            s.append(this.playerEntityList.get(i).getCommandSenderName());
        }
        return s.toString();
    }

    public StatisticsFile func_148538_i(String name) {
        return this.getPlayerStatsFile(this.func_72361_f(name));
    }

    public GameProfile getProfileFromName(String name) {
        EntityPlayerMP player = this.func_72361_f(name);
        if (player == null) {
            CompatLib.LOGGER.error("[ServerConfigurationManager] Cannot get offline player by name: " + name);
            return null;
        } else {
            GameProfile profile = player.getGameProfile();
            if (profile == null) {
                CompatLib.LOGGER.error("[ServerConfigurationManager] Cannot get offline player profile by name: " + name);
            }
            return profile;
        }
    }

    @SuppressWarnings("DataFlowIssue")
    @Inject(
        method = "initializeConnectionToPlayer",
        at = @At(
            value = "INVOKE",
            target = "Lcpw/mods/fml/common/FMLCommonHandler;firePlayerLoggedIn(Lnet/minecraft/entity/player/EntityPlayer;)V",
            shift = At.Shift.AFTER
        )
    )
    public void initializeConnectionToPlayer(NetworkManager manager, EntityPlayerMP player, NetHandlerPlayServer handler, CallbackInfo ci) {
        FMLNetworkHandler.handlePlayerLogin(player, (NetServerHandler) (Object) handler, (INetworkManager) manager);
    }
}
