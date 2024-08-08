package space.libs.mixins;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.BanList;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.stats.StatisticsFile;
import net.minecraft.world.WorldSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

@SuppressWarnings("all")
@Mixin(ServerConfigurationManager.class)
public abstract class MixinServerConfigurationManager {

    @Shadow
    private static @Final Logger logger = LogManager.getLogger();

    @Shadow
    private @Final MinecraftServer mcServer;

    @Shadow
    private WorldSettings.GameType gameType;

    @Shadow
    public @Final List<EntityPlayerMP> playerEntityList;

    @Shadow
    public abstract boolean func_152596_g(GameProfile profile);

    /** remove */
    @Shadow
    public void func_152597_c(GameProfile profile) {}

    /** add */
    @Shadow
    public void func_152601_d(GameProfile profile) {}

    @Shadow
    public abstract StatisticsFile func_152602_a(EntityPlayer player);

    @Shadow
    public void func_152605_a(GameProfile profile) {}

    @Shadow
    public abstract boolean func_152607_e(GameProfile profile);

    @Shadow
    public void func_152610_b(GameProfile profile) {}

    public boolean func_72353_e(String name) {
        try {
            return this.func_152596_g(this.getProfileFromName(name));
        } catch (Exception e) {
            return false;
        }
    }

    /** setGameType */
    public void func_72357_a(WorldSettings.GameType par1EnumGameType) {
        this.gameType = par1EnumGameType;
    }

    public void func_72359_h(String name) {
        try {
            this.func_152610_b(this.getProfileFromName(name));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void func_72360_c(String name) {
        try {
            this.func_152601_d(this.getProfileFromName(name));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** getPlayerForUsername */
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

    public boolean func_72370_d(String name) {
        try {
            return this.func_152607_e(this.getProfileFromName(name));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Set func_72376_i() {
        throw new UnsupportedOperationException();
    }

    public void func_72379_i(String name) {
        try {
            this.func_152597_c(this.getProfileFromName(name));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void func_72386_b(String name) {
        try {
            this.func_152605_a(this.getProfileFromName(name));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Set func_72388_h() {
        throw new UnsupportedOperationException();
    }

    public BanList func_72390_e() {
        throw new UnsupportedOperationException();
    }

    /** getPlayerListAsString */
    public String func_72398_c() {
        String s = "";
        for (int i = 0; i < this.playerEntityList.size(); i++) {
            if (i > 0)
                s = s + ", ";
            s = s + ((EntityPlayerMP)this.playerEntityList.get(i)).getCommandSenderName();
        }
        return s;
    }

    public StatisticsFile func_148538_i(String p_148538_1_) {
        return this.func_152602_a(this.func_72361_f(p_148538_1_));
    }

    public GameProfile getProfileFromName(String name) {
        try {
            GameProfile profile = this.func_72361_f(name).getGameProfile();
            return profile;
        } catch (Exception e) {
            logger.error("[CompatLib] Cannot get offline player by name: " + name);
            e.printStackTrace();
            return null;
        }
    }
}
