package br.com.blecaute.sigaa.api.annotation.validator.impl;

import br.com.blecaute.sigaa.api.annotation.validator.annotation.ClassValidator;
import br.com.blecaute.sigaa.api.annotation.validator.Validator;
import lombok.NonNull;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ClassValidatorImpl implements Validator<ClassValidator> {

    @Override
    public boolean validate(@NonNull ClassValidator annotation, @NonNull Element element) {
        return annotation.value().isBlank() || element.className().matches(annotation.value());
    }

    @Override
    public boolean validate(@NonNull ClassValidator annotation, @NonNull Elements elements) {
        return elements.stream().anyMatch(element -> validate(annotation, element));
    }

}