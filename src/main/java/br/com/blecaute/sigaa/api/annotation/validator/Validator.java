package br.com.blecaute.sigaa.api.annotation.validator;

import lombok.NonNull;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.annotation.Annotation;

/**
 * Represents a validator of an annotation that checks
 * whether the element is valid.
 *
 * @param <T> The annotation type.
 */
public interface Validator<T extends Annotation> {

    /**
     * Validate the @{@link Element}
     *
     * @param annotation The annotation.
     * @param element The element.
     *
     * @return True if the element is valid, otherwise false.
     */
    boolean validate(@NonNull T annotation, @NonNull Element element);

    /**
     * Validate the @{@link Elements}
     *
     * @param annotation The annotation.
     * @param elements The elements.
     *
     * @return rue if the elements is valid, otherwise false.
     */
    boolean validate(@NonNull T annotation, @NonNull Elements elements);

}