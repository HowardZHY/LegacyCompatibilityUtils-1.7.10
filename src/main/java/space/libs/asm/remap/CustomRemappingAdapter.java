/*
 * Forge Mod Loader
 * Copyright (c) 2012-2013 cpw.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * https://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 */

package space.libs.asm.remap;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.*;

public class CustomRemappingAdapter extends RemappingClassAdapter {

    public static DefaultRemapper[] INSTANCES = new DefaultRemapper[16];

    public static CustomRemappingAdapter Default(ClassVisitor cv) {
        return new CustomRemappingAdapter(cv, new DefaultRemapper(), 1);
    }

    public static CustomRemappingAdapter Legacy(ClassVisitor cv) {
        return new CustomRemappingAdapter(cv, new CustomRemapper(DefaultRemapper.LEGACY_MAPPINGS), 10);
    }

    public CustomRemappingAdapter(ClassVisitor cv, DefaultRemapper instance, int id) {
        super(cv, instance);
        INSTANCES[id] = instance;
        this.id = id;
        this.legacy = id > 9;
    }

    public final int id;

    public final boolean legacy;

    public DefaultRemapper getRemapper() {
        return INSTANCES[id];
    }

    public CustomRemapper getCustomRemapper() {
        return (CustomRemapper) INSTANCES[10];
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        if (interfaces == null) {
            interfaces = new String[0];
        }
        if (legacy) {
            this.getCustomRemapper().mergeSuperMaps(name, superName, interfaces);
        } else {
            this.getRemapper().mergeSuperMaps(name, superName, interfaces);
        }
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    protected MethodVisitor createRemappingMethodAdapter(int access, String newDesc, MethodVisitor mv) {
        return new FixingMethodAdapter(access, newDesc, mv, this);
    }

    public static class FixingMethodAdapter extends RemappingMethodAdapter {

        private final CustomRemappingAdapter instance;

        public FixingMethodAdapter(int access, String desc, MethodVisitor mv, CustomRemappingAdapter instance) {
            super(access, desc, mv, instance.remapper);
            this.instance = instance;
        }

        @Override
        public void visitFieldInsn(int opcode, String owner, String name, String desc) {
            // This method solves the problem of a static field reference changing type. In all probability it is a
            // compatible change, however we need to fix up the desc to point at the new type
            String type;
            String fieldName;
            String newDesc;
            if (instance.legacy) {
                type = instance.getCustomRemapper().mapType(owner);
                fieldName = instance.getCustomRemapper().mapFieldName(owner, name, desc);
                newDesc = instance.getCustomRemapper().mapDesc(desc);
            } else { // Not needed anymore for legacy?
                type = instance.getRemapper().mapType(owner);
                fieldName = instance.getRemapper().mapFieldName(owner, name, desc);
                newDesc = instance.getRemapper().mapDesc(desc);
                if ((opcode == Opcodes.GETSTATIC) && type.startsWith("net/minecraft/") && newDesc.startsWith("Lnet/minecraft/")) {
                    String replDesc = instance.getRemapper().getStaticFieldType(owner, name, type, fieldName);
                    if (replDesc != null) {
                        newDesc = instance.getRemapper().mapDesc(replDesc);
                    }
                }
            }
            if (mv != null) {
                mv.visitFieldInsn(opcode, type, fieldName, newDesc);
            }
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
        }
    }
}
