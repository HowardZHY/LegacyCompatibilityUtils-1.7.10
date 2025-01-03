/*
 * Forge Mod Loader
 * Copyright (c) 2012-2013 cpw.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * https://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 */

package space.libs.asm;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.Remapper;
import org.objectweb.asm.commons.RemappingClassAdapter;
import org.objectweb.asm.commons.RemappingMethodAdapter;

public class CustomRemappingAdapter extends RemappingClassAdapter {

    public static CustomRemappingAdapter INSTANCE;

    public CustomRemappingAdapter(ClassVisitor cv) {
        super(cv, new CustomRemapRemapper());
        INSTANCE = this;
    }

    public CustomRemapRemapper getRemapper() {
        return (CustomRemapRemapper) super.remapper;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        if (interfaces == null) {
            interfaces = new String[0];
        }
        this.getRemapper().mergeSuperMaps(name, superName, interfaces);
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    protected MethodVisitor createRemappingMethodAdapter(int access, String newDesc, MethodVisitor mv) {
        return new StaticFixingMethodVisitor(access, newDesc, mv, remapper);
    }

    public class StaticFixingMethodVisitor extends RemappingMethodAdapter {

        public StaticFixingMethodVisitor(int access, String desc, MethodVisitor mv, Remapper remapper) {
            super(access, desc, mv, remapper);
        }

        @Override
        public void visitFieldInsn(int opcode, String originalType, String originalName, String desc) {
            // This method solves the problem of a static field reference changing type. In all probability it is a
            // compatible change, however we need to fix up the desc to point at the new type
            String type = remapper.mapType(originalType);
            String fieldName = remapper.mapFieldName(originalType, originalName, desc);
            String newDesc = remapper.mapDesc(desc);
            if (opcode == Opcodes.GETSTATIC) // && type.startsWith("net/minecraft/") && newDesc.startsWith("Lnet/minecraft/"))
            {
                String replDesc = getRemapper().getStaticFieldType(originalType, originalName, type, fieldName);
                if (replDesc != null) {
                    newDesc = remapper.mapDesc(replDesc);
                }
            }
            // super.super
            if (mv != null) {
                mv.visitFieldInsn(opcode, type, fieldName, newDesc);
            }
        }
    }
}
