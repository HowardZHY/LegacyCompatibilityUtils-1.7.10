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

package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;

@SuppressWarnings("unused")
public class EntityRendererProxy extends EntityRenderer {

    public static final String fmlMarker = "This is an FML marker";

    public Minecraft game;

    public EntityRendererProxy(Minecraft minecraft) {
        super(minecraft, minecraft.getResourceManager());
        game = minecraft;
    }

    @Override
    public void updateCameraAndRender(float tick) {
        super.updateCameraAndRender(tick);
        //This is where ModLoader does all of it's ticking
    }
}
