package br.com.blecaute.sigaa.api.annotation.selector.processor.impl;

import br.com.blecaute.sigaa.api.annotation.selector.DateSelector;
import br.com.blecaute.sigaa.api.annotation.selector.Selector;
import br.com.blecaute.sigaa.api.annotation.selector.processor.Processor;
import br.com.blecaute.sigaa.api.mapper.Mappers;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.jsoup.nodes.Element;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class DateProcessorImpl implements Processor<DateSelector> {

    @Override @SneakyThrows
    public Object process(@NonNull Field field, @NonNull DateSelector dateSelector, @NonNull Element document) {
        final var processor = Objects.requireNonNull(Mappers.getProcessor(Selector.class));
        final var value = processor.process(field, dateSelector.selector(), document);

        if (value != null) {
            final var format = new SimpleDateFormat(dateSelector.pattern());
            return format.parse(value.toString());
        }

        return null;
    }

}
