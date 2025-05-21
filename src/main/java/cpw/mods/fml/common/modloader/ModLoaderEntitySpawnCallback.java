/*
 * Forge Mod Loader
 * Copyright (c) 2012-2013 cpw.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * Contributors:
 *     cpw - implementation
 */

package cpw.mods.fml.common.modloader;

import com.google.common.base.Function;
import cpw.mods.fml.common.network.internal.FMLMessage;
import cpw.mods.fml.common.registry.EntityRegistry.EntityRegistration;
import net.minecraft.entity.Entity;
import space.libs.fml.EntitySpawnPacket;

@SuppressWarnings("unused")
public class ModLoaderEntitySpawnCallback implements Function<FMLMessage.EntitySpawnMessage, Entity> {

    public BaseModProxy mod;

    public EntityRegistration registration;

    public boolean isAnimal;

    public ModLoaderEntitySpawnCallback(BaseModProxy mod, EntityRegistration er) {
        this.mod = mod;
        this.registration = er;
    }

    @Override
    public Entity apply(FMLMessage.EntitySpawnMessage input) {
        return ModLoaderHelper.sidedHelper.spawnEntity(mod, (EntitySpawnPacket) input, registration);
    }
}
