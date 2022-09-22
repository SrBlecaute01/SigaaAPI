package br.com.blecaute.sigaa.api.annotation.selector;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that field is a collection.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CollectionSelector {

    /**
     * The location of the html element.
     * @return The location of the html element.
     */
    String value();

    /**
     * Indicate the location of collection value.
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