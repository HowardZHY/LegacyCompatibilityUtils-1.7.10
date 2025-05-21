/*
 * Forge Mod Loader
 * Copyright (c) 2012-2013 cpw.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * https://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 *
 * Contributors:
 *     cpw - implementation
 */

package cpw.mods.fml.common.modloader;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.network.*;
import cpw.mods.fml.common.network.internal.FMLMessage;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import net.minecraft.command.ICommand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.src.TradeEntry;
import space.libs.fml.*;
import space.libs.interfaces.IEntityRegistry;
import space.libs.interfaces.IFMLCommonHandler;

import java.util.EnumSet;
import java.util.Map;

@SuppressWarnings("unused")
public class ModLoaderHelper {

    public static IModLoaderSidedHelper sidedHelper;

    public static Map<BaseModProxy, ModLoaderGuiHelper> guiHelpers = Maps.newHashMap();

    public static Map<Integer, ModLoaderGuiHelper> guiIDs = Maps.newHashMap();

    public static void updateStandardTicks(BaseModProxy mod, boolean enable, boolean useClock) {
        ModLoaderModContainer mlmc = (ModLoaderModContainer) Loader.instance().getReversedModObjectList().get(mod);
        if (mlmc == null) {
            mlmc = (ModLoaderModContainer) Loader.instance().activeModContainer();
        }
        if (mlmc == null) {
            FMLLog.severe("Attempted to register ModLoader ticking for invalid BaseMod %s", mod);
            return;
        }
        BaseModTicker ticker = mlmc.getGameTickHandler();
        EnumSet<TickEvent.Type> ticks = ticker.ticks();
        // If we're enabled we get render ticks
        if (enable && !useClock) {
            ticks.add(TickEvent.Type.RENDER);
        } else {
            ticks.remove(TickEvent.Type.RENDER);
        }
        // If we're enabled, but we want clock ticks, or we're server side we get game ticks
        if (enable && (useClock || FMLCommonHandler.instance().getSide().isServer())) {
            ticks.add(TickEvent.Type.CLIENT);
            ticks.add(IFMLCommonHandler.WORLDLOAD);
        } else {
            ticks.remove(TickEvent.Type.CLIENT);
            ticks.remove(IFMLCommonHandler.WORLDLOAD);
        }
    }

    public static void updateGUITicks(BaseModProxy mod, boolean enable, boolean useClock) {
        ModLoaderModContainer mlmc = (ModLoaderModContainer) Loader.instance().getReversedModObjectList().get(mod);
        if (mlmc == null) {
            mlmc = (ModLoaderModContainer) Loader.instance().activeModContainer();
        }
        if (mlmc == null) {
            FMLLog.severe("Attempted to register ModLoader ticking for invalid BaseMod %s", mod);
            return;
        }
        EnumSet<TickEvent.Type> ticks = mlmc.getGUITickHandler().ticks();
        // If we're enabled, and we don't want clock ticks we get render ticks
        if (enable && !useClock) {
            ticks.add(TickEvent.Type.RENDER);
        } else {
            ticks.remove(TickEvent.Type.RENDER);
        }
        // If we're enabled, but we want clock ticks, or we're server side we get world ticks
        if (enable && useClock) {
            ticks.add(TickEvent.Type.CLIENT);
            ticks.add(IFMLCommonHandler.WORLDLOAD);
        } else {
            ticks.remove(TickEvent.Type.CLIENT);
            ticks.remove(IFMLCommonHandler.WORLDLOAD);
        }
    }

    /*public static IPacketHandler buildPacketHandlerFor(BaseModProxy mod) {
        return new ModLoaderPacketHandler(mod);
    }*/

    public static IWorldGenerator buildWorldGenHelper(BaseModProxy mod) {
        return new ModLoaderWorldGenerator(mod);
    }

    public static IFuelHandler buildFuelHelper(BaseModProxy mod) {
        return new ModLoaderFuelHelper(mod);
    }

    public static ICraftingHandler buildCraftingHelper(BaseModProxy mod) {
        return new ModLoaderCraftingHelper(mod);
    }

    public static void finishModLoading(ModLoaderModContainer mc) {
        if (sidedHelper != null) {
            sidedHelper.finishModLoading(mc);
        }
    }

    /*public static IConnectionHandler buildConnectionHelper(BaseModProxy mod) {
        return new ModLoaderConnectionHandler(mod);
    }*/

    public static IPickupNotifier buildPickupHelper(BaseModProxy mod) {
        return new ModLoaderPickupNotifier(mod);
    }

    public static void buildGuiHelper(BaseModProxy mod, int id) {
        ModLoaderGuiHelper handler = guiHelpers.get(mod);
        if (handler == null) {
            handler = new ModLoaderGuiHelper(mod);
            guiHelpers.put(mod,handler);
            NetworkRegistry.INSTANCE.registerGuiHandler(mod, handler);
        }
        handler.associateId(id);
        guiIDs.put(id, handler);
    }

    public static void openGui(int id, EntityPlayer player, Container container, int x, int y, int z) {
        ModLoaderGuiHelper helper = guiIDs.get(id);
        helper.injectContainerAndID(container, id);
        player.openGui(helper.getMod(), id, player.worldObj, x, y, z);
    }

    public static Object getClientSideGui(BaseModProxy mod, EntityPlayer player, int ID, int x, int y, int z)
    {
        if (sidedHelper != null)
        {
            return sidedHelper.getClientGui(mod, player, ID, x, y, z);
        }
        return null;
    }

    @SuppressWarnings("all")
    public static void buildEntityTracker(BaseModProxy mod, Class<? extends Entity> entityClass, int entityTypeId, int updateRange, int updateInterval, boolean sendVelocityInfo) {
        IEntityRegistry accessor = (IEntityRegistry) EntityRegistry.instance();
        EntityRegistry.EntityRegistration er = accessor.registerModLoader(mod, entityClass, entityTypeId, updateRange, updateInterval, sendVelocityInfo);
        Function<FMLMessage.EntitySpawnMessage, Entity> func = new ModLoaderEntitySpawnCallback(mod, er);
        er.setCustomSpawning(func, EntityDragon.class.isAssignableFrom(entityClass) || IAnimals.class.isAssignableFrom(entityClass));
    }

    public static ModLoaderVillageTradeHandler[] tradeHelpers = new ModLoaderVillageTradeHandler[6];

    public static void registerTrade(int profession, TradeEntry entry) {
        assert profession < tradeHelpers.length : "The profession is out of bounds";
        if (tradeHelpers[profession] == null) {
            tradeHelpers[profession] = new ModLoaderVillageTradeHandler();
            VillagerRegistry.instance().registerVillageTradeHandler(profession, tradeHelpers[profession]);
        }
        tradeHelpers[profession].addTrade(entry);
    }

    public static void addCommand(ICommand command) {
        ModLoaderModContainer mlmc = (ModLoaderModContainer) Loader.instance().activeModContainer();
        if (mlmc!=null) {
            mlmc.addServerCommand(command);
        }
    }

    public static IChatListener buildChatListener(BaseModProxy mod) {
        return new ModLoaderChatListener(mod);
    }
}
