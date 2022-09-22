package br.com.blecaute.sigaa.api.annotation.validator.annotation;

import org.intellij.lang.annotations.Language;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates which class of a html element
 * is valid for that field.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface ClassValidator {

    /**
     * The class name.
     * @return The class name.
     */
    @Language("RegExp")
    String value();

}