package br.com.blecaute.sigaa.api.annotation.validator;

import br.com.blecaute.sigaa.api.annotation.validator.impl.ClassValidatorImpl;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class Validators {

    private static final Map<Class<? extends Annotation>, Validator> validators = new HashMap<>();

    static {
        validators.put(ClassValidator.class, new ClassValidatorImpl());
    }

    public static boolean validate(@NotNull Class<?> clazz, @NotNull Element element) {
        for (Map.Entry<Class<? extends Annotation>, Validator> entry : validators.entrySet()) {
            Annotation annotation = clazz.getAnnotation(entry.getKey());
            if (validate(annotation, entry.getValue(), element)) {
                return false;
            }
        }

        return true;
    }

    public static boolean validate(@NotNull Field field, @NotNull Element element) {
        for (Map.Entry<Class<? extends Annotation>, Validator> entry : validators.entrySet()) {
            Annotation annotation = field.getAnnotation(entry.getKey());
            if (validate(annotation, entry.getValue(), element)) {
                return false;
            }
        }

        return true;
    }

    public static boolean validate(@NotNull Field field, @NotNull Elements elements) {
        for (Map.Entry<Class<? extends Annotation>, Validator> entry : validators.entrySet()) {
            Annotation annotation = field.getAnnotation(entry.getKey());
            if (validate(annotation, entry.getValue(), elements)) {
                return false;
            }
        }

        return true;
    }

    @SuppressWarnings("unchecked")
    private static boolean validate(Annotation annotation, Validator validator, Element element) {
        return annotation != null && !validator.validate(annotation, element);
    }

    private static boolean validate(Annotation annotation, Validator validator, Elements elements) {
        return annotation != null && !validator.validate(annotation, elements);
    }

}
