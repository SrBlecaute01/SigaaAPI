package br.com.blecaute.sigaa.api.processor;

import br.com.blecaute.sigaa.api.annotation.selector.CollectionSelector;
import br.com.blecaute.sigaa.api.annotation.selector.DateSelector;
import br.com.blecaute.sigaa.api.annotation.selector.MapSelector;
import br.com.blecaute.sigaa.api.annotation.selector.Selector;
import br.com.blecaute.sigaa.api.processor.impl.*;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public interface Processor<T extends Annotation> {

    @SneakyThrows
    void process(@NotNull Object object,
                 @NotNull Field field,
                 @NotNull T annotation,
                 @NotNull Element element);

    @SneakyThrows
    Object parse(@NotNull Field field, @NotNull T annotation, @NotNull Element element);

    static <T> T process(@NotNull T t, @NotNull Element document) {
        final var selectorProcessor = new SelectorProcessorImpl();
        final var dateProcessor = new DateProcessorImpl();
        final var mapProcessor = new MapProcessorImpl();
        final var collectionProcessor = new CollectionProcessorImpl();

        final var fields = new ArrayList<Field>();

        var clazz = t.getClass();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                fields.add(field);
            }
            clazz = clazz.getSuperclass();
        }

        for (Field field : fields) {
            final var selector = field.getAnnotation(Selector.class);
            if (selector != null) {
                selectorProcessor.process(t, field, selector, document);
                continue;
            }

            final var dateSelector = field.getAnnotation(DateSelector.class);
            if (dateSelector != null) {
                dateProcessor.process(t, field, dateSelector, document);
                continue;
            }

            final var mapSelector = field.getAnnotation(MapSelector.class);
            if (mapSelector != null) {
                mapProcessor.process(t, field, mapSelector, document);
                continue;
            }

            final var collectionSelector = field.getAnnotation(CollectionSelector.class);
            if (collectionSelector != null) {
                collectionProcessor.process(t, field, collectionSelector, document);
            }
        }


        return t;
    }


}
