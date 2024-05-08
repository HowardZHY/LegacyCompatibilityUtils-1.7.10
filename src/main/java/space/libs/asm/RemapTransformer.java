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

import net.minecraft.launchwrapper.IClassNameTransformer;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.Remapper;
import org.spongepowered.asm.mixin.extensibility.IRemapper;

public abstract class RemapTransformer extends Remapper implements IClassTransformer, IRemapper, IClassNameTransformer {

    /** Is it the start of sth? */
    // public static File MAPPINGS_FOLDER = new File("mappings");

    public RemapTransformer INSTANCE;

    public RemapTransformer() {
        INSTANCE = this;
    }

    @Override
    public final byte[] transform(String name, String transformedName, byte[] bytes) {
        if (name == null || bytes == null) {
            return null;
        }
        if (ClassNameList.Contains(name) || ClassNameList.Startswith(name)) {
            return bytes;
        }
        ClassReader reader = new ClassReader(bytes);
        ClassWriter writer = new ClassWriter(reader, 0);
        reader.accept(createClassRemapper(reader, writer), 0);
        return writer.toByteArray();
    }

    ClassVisitor createClassRemapper(ClassReader reader, ClassVisitor cv) {
        return new ClassRemapper(cv, this);
    }

    @Override
    public String unmap(String typeName) {
        return typeName;
    }

    // Copied from Remapper#mapDesc with references to 'map' replaced with 'unmap'
    @Override
    public String unmapDesc(String desc) {
        Type t = Type.getType(desc);
        switch (t.getSort()) {
            case Type.ARRAY:
                String s = unmapDesc(t.getElementType().getDescriptor());
                StringBuilder sb = new StringBuilder(s.length());
                for (int i = 0; i < t.getDimensions(); ++i) {
                    sb.append('[');
                }
                sb.append(s);
                return sb.toString();
            case Type.OBJECT:
                String newType = unmap(t.getInternalName());
                if (newType != null) {
                    return 'L' + newType + ';';
                }
        }
        return desc;
    }
}
