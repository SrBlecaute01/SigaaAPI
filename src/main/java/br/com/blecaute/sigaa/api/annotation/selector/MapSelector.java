package br.com.blecaute.sigaa.api.annotation.selector;

import org.intellij.lang.annotations.RegExp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MapSelector {

    Class<?> value();

    String selector() default "";

    boolean child() default true;

    @RegExp
    String step() default "";

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @interface Key {

        String value();

    }

}
