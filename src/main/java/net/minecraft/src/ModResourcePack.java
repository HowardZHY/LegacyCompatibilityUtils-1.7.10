package net.minecraft.src;

import com.google.gson.JsonObject;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.*;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

@SuppressWarnings("unused")
@SideOnly(Side.CLIENT)
public class ModResourcePack implements IResourcePack {

    private final Class<?> modClass;

    public ModResourcePack(Class<?> modClass) {
        this.modClass = modClass;
    }

    @Override
    public InputStream getInputStream(ResourceLocation id) {
        return this.modClass.getResourceAsStream("/assets/minecraft/" + id.getResourcePath());
    }

    @Override
    public boolean resourceExists(ResourceLocation id) {
        try {
            return this.getInputStream(id) != null;
        } catch (RuntimeException var3) {
            return false;
        }
    }

    @Override
    public Set<?> getResourceDomains() {
        return DefaultResourcePack.defaultResourceDomains;
    }

    @Override
    public IMetadataSection getPackMetadata(IMetadataSerializer serializer, String key) {
        return serializer.parseMetadataSection(key, new JsonObject());
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public BufferedImage getPackImage() {
        try {
            InputStream is = DefaultResourcePack.class.getResourceAsStream("/" + (new ResourceLocation("pack.png")).getResourcePath());
            return ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getPackName() {
        return this.modClass.getSimpleName();
    }
}
