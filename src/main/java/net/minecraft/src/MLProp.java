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

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@SuppressWarnings("unused")
@Retention(value = RUNTIME)
@Target(value = FIELD)
public @interface MLProp {

    /**
     * Adds additional help to top of configuration file.
     */
    String info() default "";

    /**
     * Maximum value allowed if field is a number.
     */
    double max() default Double.MAX_VALUE;

    /**
     * Minimum value allowed if field is a number.
     */
    double min() default Double.MIN_VALUE;

    /**
     * Overrides the field name for property key.
     */
    String name() default "";

}
