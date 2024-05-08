package space.libs.mixins;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.world.WorldSettings;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Iterator;
import java.util.List;

@SuppressWarnings("all")
@Mixin(ServerConfigurationManager.class)
public class MixinServerConfigurationManager {

    @Shadow
    private WorldSettings.GameType gameType;

    @Final
    @Shadow
    public List<EntityPlayerMP> playerEntityList;

    /** setGameType */
    public void func_72357_a(WorldSettings.GameType par1EnumGameType) {
        this.gameType = par1EnumGameType;
    }

    /** getPlayerForUsername */
    public EntityPlayerMP func_72361_f(String par1Str) {
        EntityPlayerMP entityplayermp;
        Iterator<EntityPlayerMP> iterator = this.playerEntityList.iterator();
        do {
            if (!iterator.hasNext())
                return null;
            entityplayermp = iterator.next();
        } while (!entityplayermp.getCommandSenderName().equalsIgnoreCase(par1Str));
        return entityplayermp;
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
}
