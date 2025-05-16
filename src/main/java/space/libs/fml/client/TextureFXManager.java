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

package space.libs.fml.client;

import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import space.libs.CompatLib;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

@SuppressWarnings("unused")
public class TextureFXManager {

    public static final TextureFXManager INSTANCE = new TextureFXManager();

    public Minecraft client;

    public Map<Integer,TextureHolder> texturesById = Maps.newHashMap();

    public Map<String, TextureHolder> texturesByName = Maps.newHashMap();

    public void setClient(Minecraft client) {
        this.client = client;
    }

    public static TextureFXManager instance() {
        return INSTANCE;
    }

    public void fixTransparency(BufferedImage loadedImage, String textureName) {
        if (textureName.matches("^/mob/.*_eyes.*.png$")) {
            for (int x = 0; x < loadedImage.getWidth(); x++) {
                for (int y = 0; y < loadedImage.getHeight(); y++) {
                    int argb = loadedImage.getRGB(x, y);
                    if ((argb & 0xff000000) == 0 && argb != 0) {
                        loadedImage.setRGB(x, y, 0);
                    }
                }
            }
        }
    }

    public void bindTextureToName(String name, int index) {
        TextureHolder holder = new TextureHolder();
        holder.textureId = index;
        holder.textureName = name;
        texturesById.put(index,holder);
        texturesByName.put(name,holder);
    }

    public void setTextureDimensions(int index, int j, int k) {
        TextureHolder holder = texturesById.get(index);
        if (holder == null) {
            return;
        }
        holder.x = j;
        holder.y = k;
    }

    public void addNewTextureOverride(String textureToOverride, String overridingTexturePath, int location) {
        CompatLib.LOGGER.warn("1.4.7 Mod?");
    }

    public static class TextureHolder {

        public int textureId;

        public String textureName;

        public int x;

        public int y;

    }

    public Dimension getTextureDimensions(String texture) {
        return texturesByName.containsKey(texture) ? new Dimension(texturesByName.get(texture).x, texturesByName.get(texture).y) : new Dimension(1,1);
    }

}
