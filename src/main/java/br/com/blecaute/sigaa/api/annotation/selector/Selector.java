package br.com.blecaute.sigaa.api.annotation.selector;

import org.intellij.lang.annotations.Language;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates the location of the html element for that field.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Selector {

    /**
     * The css selector for the html element.
     * @return The css selector.
     */
    String value();

    /**
     * The attribute of the element.
     * @return The attribute of the element.
     */
    String attr() default "";

    /**
     * The exclusion pattern.
     * @return The exclusion pattern.
     */
    @Language("RegExp")
    String exclusion() default "";

    /**
     * Indicate if the element is the own text.
     * @return True if element is the own text, otherwise false.
     */
    boolean ownText() default false;

    /**
     * Indicate if the element is the first.
     * @return True if element is the first, otherwise false.
     */
    boolean first() default true;

}