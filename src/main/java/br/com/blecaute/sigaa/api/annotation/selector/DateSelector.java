package br.com.blecaute.sigaa.api.annotation.selector;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that field is a date.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DateSelector {

    /**
     * The date pattern.
     * @return The date pattern.
     */
    String pattern() default "dd/MM/yyyy HH:mm";

    /**
     * The location of the html element.
     * @return The location of the html element.
     */
    Selector selector();

}