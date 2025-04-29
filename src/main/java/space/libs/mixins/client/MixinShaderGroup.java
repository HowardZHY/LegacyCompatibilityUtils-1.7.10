package space.libs.mixins.client;

import com.google.gson.*;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.shader.*;
import net.minecraft.client.util.JsonException;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.io.InputStream;
import java.util.*;

@SuppressWarnings("unused")
@Mixin(ShaderGroup.class)
public abstract class MixinShaderGroup {

    @Shadow
    private @Final IResourceManager resourceManager;

    @Shadow
    private void initUniform(JsonElement jsonElement) {}

    @Shadow
    public abstract Shader addShader(String string, Framebuffer framebuffer, Framebuffer framebuffer2);

    @Shadow
    private Framebuffer getFramebuffer(String string) {
        throw new AbstractMethodError();
    }

    @Shadow
    protected abstract void initTarget(JsonElement jsonElement);

    /** initPass*/
    public void func_148019_b(JsonElement jsonElement) throws JsonException {
        JsonObject pass = JsonUtils.getElementAsJsonObject(jsonElement, "pass");
        String name = JsonUtils.getJsonObjectStringFieldValue(pass, "name");
        String in = JsonUtils.getJsonObjectStringFieldValue(pass, "intarget");
        String out = JsonUtils.getJsonObjectStringFieldValue(pass, "outtarget");
        Framebuffer bufferInput = this.getFramebuffer(in);
        Framebuffer bufferOutput = this.getFramebuffer(out);
        if (bufferInput == null) {
            throw new JsonException("Input target " + in + " does not exist");
        } else if (bufferOutput == null) {
            throw new JsonException("Output target " + out + " does not exist");
        } else {
            Shader shader = this.addShader(name, bufferInput, bufferOutput);
            JsonArray targets = JsonUtils.getJsonObjectJsonArrayFieldOrDefault(pass, "auxtargets", null);
            if (targets != null) {
                int i = 0;
                for (Iterator<JsonElement> it = targets.iterator(); it.hasNext(); ++i) {
                    JsonElement next = it.next();
                    try {
                        JsonObject target = JsonUtils.getElementAsJsonObject(next, "auxtarget");
                        String name1 = JsonUtils.getJsonObjectStringFieldValue(target, "name");
                        String id = JsonUtils.getJsonObjectStringFieldValue(target, "id");
                        Framebuffer buffer = this.getFramebuffer(id);
                        if(buffer == null) {
                            throw new JsonException("Render target " + id + " does not exist");
                        }
                        shader.addAuxFramebuffer(name1, buffer, buffer.framebufferTextureWidth, buffer.framebufferTextureHeight);
                    } catch (Exception e) {
                        JsonException je = JsonException.func_151379_a(e);
                        je.func_151380_a("auxtargets[" + i + "]");
                        throw je;
                    }
                }
            }
            JsonArray uniforms = JsonUtils.getJsonObjectJsonArrayFieldOrDefault(pass, "uniforms", null);
            if (uniforms != null) {
                int j = 0;
                for (Iterator<JsonElement> it1 = uniforms.iterator(); it1.hasNext(); ++j) {
                    JsonElement next1 = it1.next();
                    try {
                        this.initUniform(next1);
                    } catch (Exception e1) {
                        JsonException je1 = JsonException.func_151379_a(e1);
                        je1.func_151380_a("uniforms[" + j + "]");
                        throw je1;
                    }
                }
            }
        }
    }

    /** initFromLocation */
    public void func_148025_a(ResourceLocation resourceLocation) throws JsonException {
        JsonParser parser = new JsonParser();
        InputStream input = null;
        try {
            IResource resource = this.resourceManager.getResource(resourceLocation);
            input = resource.getInputStream();
            JsonObject jsonObject = parser.parse(IOUtils.toString(input, Charsets.UTF_8)).getAsJsonObject();
            JsonArray array;
            int i = 0;
            Iterator<JsonElement> it;
            JsonElement element;
            JsonException exception;
            if (JsonUtils.jsonObjectFieldTypeIsArray(jsonObject, "targets")) {
                array = jsonObject.getAsJsonArray("targets");
                for (it = array.iterator(); it.hasNext(); ++i) {
                    element = it.next();
                    try {
                        this.initTarget(element);
                    } catch (Exception var18) {
                        exception = JsonException.func_151379_a(var18);
                        exception.func_151380_a("targets[" + i + "]");
                        throw exception;
                    }
                }
            }
            if (JsonUtils.jsonObjectFieldTypeIsArray(jsonObject, "passes")) {
                array = jsonObject.getAsJsonArray("passes");
                for (it = array.iterator(); it.hasNext(); ++i) {
                    element = it.next();
                    try {
                        this.func_148019_b(element);
                    } catch (Exception var17) {
                        exception = JsonException.func_151379_a(var17);
                        exception.func_151380_a("passes[" + i + "]");
                        throw exception;
                    }
                }
            }
        } catch (Exception e) {
            JsonException je = JsonException.func_151379_a(e);
            je.func_151381_b(resourceLocation.getResourcePath());
            throw je;
        } finally {
            IOUtils.closeQuietly(input);
        }
    }
}
