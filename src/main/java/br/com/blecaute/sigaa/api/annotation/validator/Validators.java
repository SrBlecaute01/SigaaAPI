package br.com.blecaute.sigaa.api.annotation.validator;

import br.com.blecaute.sigaa.api.annotation.validator.impl.ClassValidatorImpl;
import lombok.NonNull;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for validating the annotations
 */
public class Validators {

    private static final Map<Class<? extends Annotation>, Validator<Annotation>> validators = new HashMap<>();

    static {
        registerValidator(ClassValidator.class, new ClassValidatorImpl());
    }

    /**
     * Register a new @{@link Validator}
     *
     * @param annotation The annotation class.
     * @param validator The validator.
     *
     * @param <T> The validator type.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Annotation> void registerValidator(@NonNull Class<T> annotation, @NonNull Validator<T> validator) {
        validators.put(annotation, (Validator<Annotation>) validator);
    }

    /**
     * Unregister a @{@link Validator}
     *
     * @param annotation The annotation class.
     */
    public static void unregisterValidator(@NonNull Class<? extends Annotation> annotation) {
        validators.remove(annotation);
    }

    /**
     * Validate a class with given @{@link Element}.
     *
     * @param clazz The class.
     * @param element The element.
     *
     * @return True if the class is valid, otherwise false.
     */
    public static boolean validate(@NonNull Class<?> clazz, @NonNull Element element) {
        for (Map.Entry<Class<? extends Annotation>, Validator<Annotation>> entry : validators.entrySet()) {
            final var annotation = clazz.getAnnotation(entry.getKey());
            if (!validate(annotation, entry.getValue(), element)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Validate a class with given @{@link Elements}.
     *
     * @param clazz The class.
     * @param elements The elements.
     *
     * @return True if the class is valid, otherwise false.
     */
    public static boolean validate(@NonNull Class<?> clazz, @NonNull Elements elements) {
        for (Map.Entry<Class<? extends Annotation>, Validator<Annotation>> entry : validators.entrySet()) {
            final var annotation = clazz.getAnnotation(entry.getKey());
            if (!validate(annotation, entry.getValue(), elements)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Validate a field with given @{@link Element}.
     *
     * @param field The field.
     * @param element The element.
     *
     * @return True if the field is valid, otherwise false.
     */
    public static boolean validate(@NonNull Field field, @NonNull Element element) {
        for (Map.Entry<Class<? extends Annotation>, Validator<Annotation>> entry : validators.entrySet()) {
            Annotation annotation = field.getAnnotation(entry.getKey());
            if (!validate(annotation, entry.getValue(), element)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Validate a class with given @{@link Elements}.
     *
     * @param field The field.
     * @param elements The elements.
     *
     * @return True if the field is valid, otherwise false.
     */
    public static boolean validate(@NonNull Field field, @NonNull Elements elements) {
        for (Map.Entry<Class<? extends Annotation>, Validator<Annotation>> entry : validators.entrySet()) {
            Annotation annotation = field.getAnnotation(entry.getKey());
            if (!validate(annotation, entry.getValue(), elements)) {
                return false;
            }
        }

        return true;
    }

    @SuppressWarnings("unchecked")
    private static boolean validate(Annotation annotation, Validator validator, Element element) {
        return annotation == null || validator.validate(annotation, element);
    }

    private static boolean validate(Annotation annotation, Validator validator, Elements elements) {
        return annotation == null || validator.validate(annotation, elements);
    }

}
