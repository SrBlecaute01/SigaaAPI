package br.com.blecaute.sigaa.api.processor.impl;

import br.com.blecaute.sigaa.api.annotation.selector.DateSelector;
import br.com.blecaute.sigaa.api.processor.Processor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;

public class DateProcessorImpl implements Processor<DateSelector> {

    @Override @SneakyThrows
    public void process(@NotNull Object object, @NotNull Field field, @NotNull DateSelector dateSelector, @NotNull Element document) {
        field.set(object, parse(field, dateSelector, document));
    }

    @Override @SneakyThrows
    public Object parse(@NotNull Field field, @NotNull DateSelector dateSelector, @NotNull Element document) {
        final var selectorProcessor = new SelectorProcessorImpl();
        final var value = selectorProcessor.parse(field, dateSelector.selector(), document);

        if (value != null) {
            final var format = new SimpleDateFormat(dateSelector.pattern());
            return format.parse(value.toString());
        }

        return null;
    }

}
