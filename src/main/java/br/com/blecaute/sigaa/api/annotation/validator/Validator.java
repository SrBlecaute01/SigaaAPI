package br.com.blecaute.sigaa.api.annotation.validator;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.annotation.Annotation;

public interface Validator<T extends Annotation> {

    @SneakyThrows
    boolean validate(@NotNull T annotation, @NotNull Element element);

    boolean validate(@NotNull T annotation, @NotNull Elements elements);

}