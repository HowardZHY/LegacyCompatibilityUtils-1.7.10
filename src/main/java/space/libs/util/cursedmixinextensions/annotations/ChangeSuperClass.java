package space.libs.util.cursedmixinextensions.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Change the super class of the annotated class.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ChangeSuperClass {
    /**
     * The super class to use.
     */
    public Class<?> value() default Object.class;
    public String name() default "";
    public boolean remap() default true;
}
