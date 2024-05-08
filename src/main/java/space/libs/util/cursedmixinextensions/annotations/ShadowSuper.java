package space.libs.util.cursedmixinextensions.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Turn annotated method into a method from super class.
 * Replace all references to it then remove the annotated method.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ShadowSuper {
    /**
     * The target method name.
     */
    public String value();

    public boolean remap() default true;
}
