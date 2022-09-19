package br.com.blecaute.sigaa.api.annotation.validator.impl;

import br.com.blecaute.sigaa.api.annotation.validator.ClassValidator;
import br.com.blecaute.sigaa.api.annotation.validator.Validator;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

public class ClassValidatorImpl implements Validator<ClassValidator> {

    @Override
    public boolean validate(@NotNull ClassValidator annotation, @NotNull Element element) {
        return annotation.value().isBlank() || element.className().matches(annotation.value());
    }

}