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

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author cpw
 *
 */
public class ModProperty {

    public String info;

    public double min;

    public double max;

    public String name;

    public Field field;

    public ModProperty(Field f, String info, Double min, Double max, String name) {
        this.field = f;
        this.info = info;
        this.min = min != null ? min : Double.MIN_VALUE;
        this.max = max != null ? max : Double.MAX_VALUE;
        this.name = name;
    }

    public ModProperty(Field field, Map<String, Object> annotationInfo) {
        this(field, (String)annotationInfo.get("info"), (Double)annotationInfo.get("min"), (Double)annotationInfo.get("max"), (String)annotationInfo.get("name"));
    }

    public String name() {
        return name;
    }

    public double min() {
        return min;
    }

    public double max() {
        return max;
    }

    public String info() {
        return info;
    }

    public Field field() {
        return field;
    }
}
