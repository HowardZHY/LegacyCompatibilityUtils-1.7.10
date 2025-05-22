package net.minecraft.src;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.modloader.ModLoaderClientHelper;
import cpw.mods.fml.client.registry.*;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.modloader.*;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.*;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.command.ICommand;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.Achievement;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import space.libs.interfaces.IGameRegistry;
import space.libs.interfaces.IRenderingRegistry;
import space.libs.interfaces.IStatBase;
import space.libs.util.BiomeUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static cpw.mods.fml.relauncher.Side.CLIENT;
import static cpw.mods.fml.relauncher.Side.SERVER;

@SuppressWarnings({"deprecation", "unused"})
public class ModLoader {

    public static final String fmlMarker = "This is an FML marker";

    public static final Map<String, Map<String, String>> localizedStrings = Collections.emptyMap();

    public static void addAchievementDesc(Achievement achievement, String name, String description) {
        IStatBase accessor = (IStatBase) achievement;
        String achName = accessor.func_75970_i();
        addLocalization(achName, name);
        addLocalization(achName+".desc", description);
    }

    public static int addAllFuel(int id, int metadata) {
        return 0;
    }

    @SideOnly(CLIENT)
    public static void addAllRenderers(Map<Class<? extends Entity>, Render> renderers) {
        // Empty?
    }

    @SideOnly(CLIENT)
    public static int addArmor(String armor) {
        return RenderingRegistry.addNewArmourRendererPrefix(armor);
    }

    public static void addBiome(BiomeGenBase biome) {
        IGameRegistry.INSTANCE.addBiomeLegacy(biome);
    }

    public static void addEntityTracker(BaseMod mod, Class<? extends Entity> entityClass, int entityTypeId, int updateRange, int updateInterval, boolean sendVelocityInfo) {
        ModLoaderHelper.buildEntityTracker(mod, entityClass, entityTypeId, updateRange, updateInterval, sendVelocityInfo);
    }

    public static void addCommand(ICommand command) {
        ModLoaderHelper.addCommand(command);
    }

    public static void addDispenserBehavior(Item item, IBehaviorDispenseItem behavior) {
        BlockDispenser.dispenseBehaviorRegistry.putObject(item, behavior);
    }

    public static void addLocalization(String key, String value) {
        addLocalization(key, "en_US", value);
    }

    public static void addLocalization(String key, String lang, String value) {
        LanguageRegistry.instance().addStringLocalization(key, lang, value);
    }

    public static void addName(Object instance, String name) {
        addName(instance, "en_US", name);
    }

    public static void addName(Object instance, String lang, String name) {
        LanguageRegistry.instance().addNameForObject(instance, lang, name);
    }

    @SideOnly(CLIENT)
    public static int addOverride(String fileToOverride, String fileToAdd) {
        return IRenderingRegistry.INSTANCE.addOverride(fileToOverride, fileToAdd);
    }

    @SideOnly(CLIENT)
    public static void addOverride(String path, String overlayPath, int index) {
        IRenderingRegistry.INSTANCE.addOverride(path, overlayPath, index);
    }

    public static void addRecipe(ItemStack output, Object... params) {
        GameRegistry.addRecipe(output, params);
    }

    public static void addShapelessRecipe(ItemStack output, Object... params) {
        GameRegistry.addShapelessRecipe(output, params);
    }

    public static void addSmelting(int input, ItemStack output) {
        GameRegistry.addSmelting(Item.getItemById(input), output, 1.0f);
    }

    public static void addSmelting(int input, ItemStack output, float experience) {
        GameRegistry.addSmelting(Item.getItemById(input), output, experience);
    }

    public static void addSpawn(Class<? extends EntityLiving> entityClass, int weightedProb, int min, int max, EnumCreatureType spawnList) {
        EntityRegistry.addSpawn(entityClass, weightedProb, min, max, spawnList, BiomeUtils.base12Biomes);
    }

    public static void addSpawn(Class<? extends EntityLiving> entityClass, int weightedProb, int min, int max, EnumCreatureType spawnList, BiomeGenBase... biomes) {
        EntityRegistry.addSpawn(entityClass, weightedProb, min, max, spawnList, biomes);
    }

    public static void addSpawn(String entityName, int weightedProb, int min, int max, EnumCreatureType spawnList) {
        EntityRegistry.addSpawn(entityName, weightedProb, min, max, spawnList, BiomeUtils.base12Biomes);
    }

    public static void addSpawn(String entityName, int weightedProb, int min, int max, EnumCreatureType spawnList, BiomeGenBase... biomes) {
        EntityRegistry.addSpawn(entityName, weightedProb, min, max, spawnList, biomes);
    }

    public static void addTrade(int profession, TradeEntry entry) {
        ModLoaderHelper.registerTrade(profession, entry);
    }

    /*public static void clientSendPacket(Packet packet) {
        PacketDispatcher.sendPacketToServer(packet);
    }*/

    public static boolean dispenseEntity(World world, double x, double y, double z, int xVel, int zVel, ItemStack item) {
        return false;
    }

    public static void genericContainerRemoval(World world, int x, int y, int z) {
        // Forge didn't impl?
    }

    public static List<BaseMod> getLoadedMods() {
        return ModLoaderModContainer.findAll(BaseMod.class);
    }

    public static Logger getLogger() {
        return Logger.getLogger("ModLoader");
    }

    @SideOnly(CLIENT)
    public static Minecraft getMinecraftInstance() {
        return FMLClientHandler.instance().getClient();
    }

    public static MinecraftServer getMinecraftServerInstance() {
        return FMLCommonHandler.instance().getMinecraftServerInstance();
    }

    public static <T, E> T getPrivateValue(Class<? super E> instanceclass, E instance, int fieldindex) {
        return ObfuscationReflectionHelper.getPrivateValue(instanceclass, instance, fieldindex);
    }

    public static <T, E> T getPrivateValue(Class<? super E> instanceclass, E instance, String field) {
        return ObfuscationReflectionHelper.getPrivateValue(instanceclass, instance, field);
    }

    @SideOnly(CLIENT)
    public static int getUniqueBlockModelID(BaseMod mod, boolean inventoryRenderer) {
        return ModLoaderClientHelper.obtainBlockModelIdFor(mod, inventoryRenderer);
    }

    public static int getUniqueEntityId() {
        return EntityRegistry.findGlobalUniqueEntityId();
    }

    /*@SideOnly(CLIENT)
    public static int getUniqueSpriteIndex(String path) {
        return SpriteHelper.getUniqueSpriteIndex(path);
    }*/

    public static boolean isChannelActive(EntityPlayer player, String channel) {
        return NetworkRegistry.INSTANCE.hasChannel(channel, SERVER);
    }

    @SideOnly(CLIENT)
    public static boolean isGUIOpen(Class<? extends GuiScreen> gui) {
        return (FMLClientHandler.instance().getClient().currentScreen != null); //&& (FMLClientHandler.instance().getClient().currentScreen.equals(gui)));
    }

    public static boolean isModLoaded(String modname) {
        return Loader.isModLoaded(modname);
    }

    public static void loadConfig() {
        // Implemented elsewhere
    }

    /*@SideOnly(CLIENT)
    public static BufferedImage loadImage(RenderEngine render, String path) throws Exception {
        return TextureFXManager.instance().loadImageFromTexturePack(renderEngine, path);
    }*/

    public static void onItemPickup(EntityPlayer player, ItemStack item) {
        // Call in from elsewhere. Unimplemented here.
    }

    @SideOnly(CLIENT)
    public static void onTick(float tick, Minecraft game) {
        // Call in from elsewhere. Unimplemented here.
    }

    @SideOnly(CLIENT)
    public static void openGUI(EntityPlayer player, GuiScreen gui) {
        FMLClientHandler.instance().displayGuiScreen(player, gui);
    }

    @Deprecated
    public static void populateChunk(IChunkProvider generator, int chunkX, int chunkZ, World world) {
        // Another Empty?
    }

    /*public static void receivePacket(Packet250CustomPayload packet) {
    // Implemented elsewhere?
    }*/

    @SideOnly(CLIENT)
    public static KeyBinding[] registerAllKeys(KeyBinding[] keys) {
        return keys;
    }

    /*@SideOnly(CLIENT)
    public static void registerAllTextureOverrides(RenderEngine cache) {

    }*/

    public static void registerBlock(Block block) {
        GameRegistry.registerBlock(block, block.getUnlocalizedName()); // Correct?
    }

    public static void registerBlock(Block block, Class<? extends ItemBlock> itemclass) {
        GameRegistry.registerBlock(block, itemclass, block.getUnlocalizedName()); // Above?
    }

    public static void registerContainerID(BaseMod mod, int id) {
        ModLoaderHelper.buildGuiHelper(mod, id);
    }

    public static void registerEntityID(Class<? extends Entity> entityClass, String entityName, int id) {
        EntityRegistry.registerGlobalEntityID(entityClass, entityName, id);
    }

    public static void registerEntityID(Class<? extends Entity> entityClass, String entityName, int id, int background, int foreground) {
        EntityRegistry.registerGlobalEntityID(entityClass, entityName, id, background, foreground);
    }

    @SideOnly(CLIENT)
    public static void registerKey(BaseMod mod, KeyBinding keyHandler, boolean allowRepeat) {
        ModLoaderClientHelper.registerKeyBinding(mod, keyHandler, allowRepeat);
    }

    public static void registerPacketChannel(BaseMod mod, String channel) {
        //NetworkRegistry.INSTANCE.registerChannel(ModLoaderHelper.buildPacketHandlerFor(mod), channel);
    }

    public static void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String id) {
        GameRegistry.registerTileEntity(tileEntityClass, id);
    }

    @SideOnly(CLIENT)
    public static void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String id, TileEntitySpecialRenderer renderer) {
        ClientRegistry.registerTileEntity(tileEntityClass, id, renderer);
    }

    public static void removeBiome(BiomeGenBase biome) {
        IGameRegistry.INSTANCE.removeBiomeLegacy(biome);
    }

    public static void removeSpawn(Class<? extends EntityLiving> entityClass, EnumCreatureType spawnList) {
        EntityRegistry.removeSpawn(entityClass, spawnList, BiomeUtils.base12Biomes);
    }

    public static void removeSpawn(Class<? extends EntityLiving> entityClass, EnumCreatureType spawnList, BiomeGenBase... biomes) {
        EntityRegistry.removeSpawn(entityClass, spawnList, biomes);
    }

    public static void removeSpawn(String entityName, EnumCreatureType spawnList) {
        EntityRegistry.removeSpawn(entityName, spawnList, BiomeUtils.base12Biomes);
    }

    public static void removeSpawn(String entityName, EnumCreatureType spawnList, BiomeGenBase... biomes) {
        EntityRegistry.removeSpawn(entityName, spawnList, biomes);
    }

    @SideOnly(CLIENT)
    public static boolean renderBlockIsItemFull3D(int modelID) {
        return RenderingRegistry.instance().renderItemAsFull3DBlock(modelID);
    }

    @SideOnly(CLIENT)
    public static void renderInvBlock(RenderBlocks renderer, Block block, int metadata, int modelID) {
        RenderingRegistry.instance().renderInventoryBlock(renderer, block, metadata, modelID);
    }

    @SideOnly(CLIENT)
    public static boolean renderWorldBlock(RenderBlocks renderer, IBlockAccess world, int x, int y, int z, Block block, int modelID) {
        return RenderingRegistry.instance().renderWorldBlock(renderer, world, x, y, z, block, modelID);
    }

    public static void saveConfig() {
        // Configuration is handled elsewhere
    }

    /*
    public static void sendPacket(Packet packet) {
        PacketDispatcher.sendPacketToServer(packet);
    }*/

    public static void serverChat(String text) {
        //TOD
    }

    /*
    public static void serverLogin(NetClientHandler handler, Packet1Login loginPacket) {
        //TOD
    }

    public static void serverSendPacket(NetServerHandler handler, Packet packet) {
        if (handler != null) {
            PacketDispatcher.sendPacketToPlayer(packet, (Player)handler.getPlayer());
        }
    }*/

    public static void serverOpenWindow(EntityPlayerMP player, Container container, int ID, int x, int y, int z) {
        ModLoaderHelper.openGui(ID, player, container, x, y, z);
    }

    public static void setInGameHook(BaseMod mod, boolean enable, boolean useClock) {
        ModLoaderHelper.updateStandardTicks(mod, enable, useClock);
    }

    public static void setInGUIHook(BaseMod mod, boolean enable, boolean useClock) {
        ModLoaderHelper.updateGUITicks(mod, enable, useClock);
    }

    public static <T, E> void setPrivateValue(Class<? super T> instanceclass, T instance, int fieldindex, E value) {
        ObfuscationReflectionHelper.setPrivateValue(instanceclass, instance, value, fieldindex);
    }

    public static <T, E> void setPrivateValue(Class<? super T> instanceclass, T instance, String field, E value) {
        ObfuscationReflectionHelper.setPrivateValue(instanceclass, instance, value, field);
    }

    public static void takenFromCrafting(EntityPlayer player, ItemStack item, IInventory matrix) {
        // Implemented elsewhere?
    }

    public static void takenFromFurnace(EntityPlayer player, ItemStack item) {
        // Implemented elsewhere?
    }

    public static void throwException(String message, Throwable e) {
        FMLCommonHandler.instance().raiseException(e, message, true);
    }

    public static void throwException(Throwable e) {
        throwException("Exception in ModLoader", e);
    }
}
