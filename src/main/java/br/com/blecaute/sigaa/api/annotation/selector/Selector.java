package br.com.blecaute.sigaa.api.annotation.selector;

import org.intellij.lang.annotations.Language;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Selector {

    String value();

    String parent() default "";

    String attribute() default "";

    @Language("RegExp")
    String exclusion() default "";

    boolean ownText() default false;

}