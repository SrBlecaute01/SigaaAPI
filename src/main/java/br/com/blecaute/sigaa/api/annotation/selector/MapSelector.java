package br.com.blecaute.sigaa.api.annotation.selector;

import org.intellij.lang.annotations.RegExp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicate that the field is a map.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MapSelector {

    /**
     * The map class.
     * @return The map class.
     */
    Class<?> value();

    /**
     * The location of the html element.
     * @return The location of the html element.
     */
    String selector() default "";

    /**
     * Indicates whether to select child elements or not.
     * @return True if select child elements, otherwise false.
     */
    boolean child() default true;

    /**
     * The step pattern.
     * @return The step pattern.
     */
    @RegExp
    String step() default "";

    /**
     * Indicates the key of the map.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @interface Key {

        /**
         * The location of the html element.
         * @return The location of the html element.
         */
        String value();

    }

}
