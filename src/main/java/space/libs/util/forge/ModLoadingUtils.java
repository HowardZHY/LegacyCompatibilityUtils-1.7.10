package space.libs.util.forge;

import com.google.common.collect.Multimap;
import cpw.mods.fml.common.LoaderException;
import cpw.mods.fml.common.ModClassLoader;
import cpw.mods.fml.common.asm.transformers.AccessTransformer;
import cpw.mods.fml.common.modloader.BaseModProxy;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.Launch;
import space.libs.CompatLib;

import java.lang.reflect.*;
import java.util.regex.Pattern;

public abstract class ModLoadingUtils {

    public static Pattern modClass;

    public static void init() {
        PatternInitializer initializer = new PatternInitializer();
        initializer.start();
    }

    @SuppressWarnings("unused")
    public static boolean find(String name) {
        CompatLib.LOGGER.info("Identified a BaseMod type Mod " + name);
        return modClass.matcher(name).find();
    }

    @SuppressWarnings("unchecked")
    public static Class<? extends BaseModProxy> loadBaseModClass(ModClassLoader loader, String modClazzName) throws Exception {
        AccessTransformer accessTransformer = null;
        for (IClassTransformer transformer : Launch.classLoader.getTransformers()) {
            if (transformer instanceof AccessTransformer) {
                accessTransformer = (AccessTransformer) transformer;
                break;
            }
        } if (accessTransformer == null) {
            throw new LoaderException("No access transformer found!");
        }
        ensurePublicAccessFor(accessTransformer, modClazzName);
        return (Class<? extends BaseModProxy>) Class.forName(modClazzName, true, loader);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void ensurePublicAccessFor(AccessTransformer at, String modClazzName) {
        try {
            Class<?> c = Class.forName("cpw.mods.fml.common.asm.transformers.AccessTransformer$Modifier");
            Constructor<?> ctor = c.getConstructor();
            ctor.setAccessible(true);
            Object instance = ctor.newInstance();
            Method m = c.getDeclaredMethod("setTargetAccess", String.class);
            m.setAccessible(true);
            m.invoke(instance, "public");
            Field f = c.getDeclaredField("modifyClassVisibility");
            f.setAccessible(true);
            f.set(instance, true);
            Field f1 = AccessTransformer.class.getDeclaredField("modifiers");
            f1.setAccessible(true);
            Multimap map = (Multimap) f1.get(at);
            map.put(modClazzName, c.cast(instance));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static class PatternInitializer extends Thread {

        @Override
        public void run() {
            modClass = Pattern.compile(".*(\\.|)(mod_[^\\s$]+)$");
        }
    }
}
