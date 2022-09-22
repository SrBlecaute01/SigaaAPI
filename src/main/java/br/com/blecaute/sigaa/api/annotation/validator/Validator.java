package br.com.blecaute.sigaa.api.annotation.validator;

import lombok.NonNull;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.annotation.Annotation;

public interface Validator<T extends Annotation> {

    boolean validate(@NonNull T annotation, @NonNull Element element);

    boolean validate(@NonNull T annotation, @NonNull Elements elements);

}