/*
 * This file is part of Sponge, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package space.libs.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;
import space.libs.asm.remap.CustomRemappingAdapter;
import space.libs.asm.visitors.DuplicateMethodVisitor;

@SuppressWarnings("unused")
public class DefaultCompatTransformer implements IClassTransformer {

    /**
     * @implNote Exclude OBF and OF base classes.
     * @implNote Try to fix duplicate method if Basemod try support some Forge thing
     */
    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (name == null || bytes == null) {
            return bytes;
        }
        if (ClassNameList.Contains(name) || ClassNameList.StartsWith(name)) {
            return bytes;
        }
        if (!name.contains(".")) {
            if (TransformerUtils.isVanillaClass(name)) {
                return bytes;
            } else {
                bytes = TransformerUtils.transformSafe(bytes, ClassWriter.COMPUTE_MAXS, DuplicateMethodVisitor.class, ClassReader.SKIP_FRAMES);
            }
        }
        ClassReader reader = new ClassReader(bytes);
        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
        reader.accept(CustomRemappingAdapter.Default(writer), ClassReader.EXPAND_FRAMES);
        return writer.toByteArray();
    }

}
