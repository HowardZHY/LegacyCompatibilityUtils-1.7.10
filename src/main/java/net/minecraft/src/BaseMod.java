package net.minecraft.src;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.*;

import static cpw.mods.fml.relauncher.Side.CLIENT;

@SuppressWarnings("unused")
public abstract class BaseMod implements cpw.mods.fml.common.modloader.BaseModProxy {

    public final boolean doTickInGame(TickEvent.Type tick, boolean tickEnd, Object... data) {
        if (tickEnd && (tick == TickEvent.Type.RENDER || tick == TickEvent.Type.CLIENT)) {
            Minecraft mc = FMLClientHandler.instance().getClient();
            boolean hasWorld = (mc.theWorld != null);
            if (hasWorld) {
                return onTickInGame((Float) data[0], mc);
            }
        } else {
            MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
            if (server != null) {
                return onTickInGame(server);
            }
        }
        return true;
    }

    @SideOnly(CLIENT)
    public final boolean doTickInGUI(TickEvent.Type tick, boolean tickEnd, Object... data) {
        Minecraft mc = FMLClientHandler.instance().getClient();
        boolean hasWorld = (mc.theWorld != null);
        if (tickEnd && (tick == TickEvent.Type.RENDER || (tick == TickEvent.Type.CLIENT && hasWorld)))
            return onTickInGUI((Float) data[0], mc, mc.currentScreen);
        return true;
    }

    public int addFuel(int id, int metadata) {
        return 0;
    }

    @SideOnly(CLIENT)
    public void addRenderer(Map<Class<? extends Entity>, Render> renderers) {}

    public void generateNether(World world, Random random, int chunkX, int chunkZ) {}

    public void generateSurface(World world, Random random, int chunkX, int chunkZ) {}

    @SideOnly(CLIENT)
    public GuiContainer getContainerGUI(EntityClientPlayerMP player, int containerID, int x, int y, int z) {
        return null;
    }

    public String getName() {
        return getClass().getSimpleName();
    }

    public String getPriorities() {
        return "";
    }

    public abstract String getVersion();

    @SideOnly(CLIENT)
    public void keyboardEvent(KeyBinding event) {}

    public abstract void load();

    public void modsLoaded() {}

    public void onItemPickup(EntityPlayer player, ItemStack item) {}

    @SideOnly(CLIENT)
    public boolean onTickInGame(float time, Minecraft minecraftInstance) {
        return false;
    }

    public boolean onTickInGame(MinecraftServer minecraftServer) {
        return false;
    }

    @SideOnly(CLIENT)
    public boolean onTickInGUI(float tick, Minecraft game, GuiScreen gui) {
        return false;
    }

    @Override
    public void clientChat(String text) {}

    /*@SideOnly(CLIENT)
    public void clientConnect(NetClientHandler handler) {}

    @SideOnly(CLIENT)
    public void clientDisconnect(NetClientHandler handler) {}

    @Override
    public void receiveCustomPacket(Packet250CustomPayload packet) {}*/

    @SideOnly(CLIENT)
    public void registerAnimation(Minecraft game) {}

    @SideOnly(CLIENT)
    public void renderInvBlock(RenderBlocks renderer, Block block, int metadata, int modelID) {}

    @SideOnly(CLIENT)
    public boolean renderWorldBlock(RenderBlocks renderer, IBlockAccess world, int x, int y, int z, Block block, int modelID) {
        return false;
    }

    @Override
    public void serverDisconnect() {}

    public void takenFromCrafting(EntityPlayer player, ItemStack item, IInventory matrix) {}

    public void takenFromFurnace(EntityPlayer player, ItemStack item) {}

    @Override
    public String toString() {
        return getName() + " " + getVersion();
    }

    //@Override
    //public void serverChat(NetServerHandler source, String message) {}

    @Override
    public void onClientLogin(EntityPlayer player) {}

    //@Override
    //public void onClientLogout(INetworkManager mgr) {}

    public Entity spawnEntity(int entityId, World world, double scaledX, double scaledY, double scaledZ) {
        return null;
    }

    //public void clientCustomPayload(NetClientHandler handler, Packet250CustomPayload packet) {}
}
