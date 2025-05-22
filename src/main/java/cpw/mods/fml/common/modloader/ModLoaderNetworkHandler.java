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

public class ModLoaderNetworkHandler //extends NetworkModHandler
{

    public BaseModProxy baseMod;

    public ModLoaderNetworkHandler(ModLoaderModContainer mlmc) {
        //super(mlmc, null);
    }

    public void setBaseMod(BaseModProxy baseMod) {
        this.baseMod = baseMod;
    }

    public boolean requiresClientSide() {
        return false;
    }

    public boolean requiresServerSide() {
        return false;
    }

    public boolean acceptVersion(String version) {
        return baseMod.getVersion().equals(version);
    }

    public boolean isNetworkMod() {
        return true;
    }
}
