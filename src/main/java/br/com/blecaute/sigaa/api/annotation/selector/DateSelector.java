package br.com.blecaute.sigaa.api.annotation.selector;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DateSelector {

    String pattern() default "dd/MM/yyyy HH:mm";

    Selector selector();

}