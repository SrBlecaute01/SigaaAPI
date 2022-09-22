package br.com.blecaute.sigaa.api.annotation.selector.processor.impl;

import br.com.blecaute.sigaa.api.annotation.selector.MapSelector;
import br.com.blecaute.sigaa.api.annotation.selector.processor.Processor;
import br.com.blecaute.sigaa.api.annotation.validator.Validators;
import br.com.blecaute.sigaa.api.mapper.Mappers;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Element;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class MapProcessorImpl implements Processor<MapSelector> {

    @Override
    public Object process(@NonNull Field field, @NonNull MapSelector mapSelector, @NonNull Element document) {
        final var keyAnnotation = mapSelector.value().getAnnotation(MapSelector.Key.class);
        if (keyAnnotation == null) {
            throw new IllegalArgumentException("The key of map selector must be present");
        }

        final var selector = mapSelector.selector();
        final var element = selector.isBlank() ? document : document.selectFirst(selector);

        if (element == null) return null;

        final var map = new HashMap<String, Object>();
        final var clazz = mapSelector.value();
        final var pattern = mapSelector.step().isBlank() ? null : Pattern.compile(mapSelector.step());

        for (Element splitElement : splitElements(element, pattern)) {
            if (mapSelector.child()) {
                splitElement.children().forEach(child -> process(keyAnnotation, clazz, child, map));
            } else {
                process(keyAnnotation, clazz, splitElement, map);
            }
        }

        return map;
    }

    @SneakyThrows
    private void process(MapSelector.Key annotation, Class<?> clazz, Element element, Map<String, Object> map) {
        if (!Validators.validate(clazz, element)) return;

        final var object = Mappers.map(clazz, element);
        final var keyElement = element.selectFirst(annotation.value());
        final var key = keyElement == null ? null : keyElement.text();

        if (StringUtil.isBlank(key)) {
            try {
                final var keyField  = clazz.getDeclaredField(annotation.value());
                keyField.setAccessible(true);

                map.put(keyField.get(object).toString(), object);

            } catch (Exception ignored) {}

        } else {
            map.put(key, object);
        }
    }

    @NotNull
    private List<Element> splitElements(Element element, Pattern pattern) {
        final var elements = new ArrayList<Element>();
        if (pattern == null) {
            elements.add(element);
            return elements;
        }

        var clone = element.shallowClone();
        clone.prependChildren(clone.children());

        var first = false;
        var append = false;

        for (Element child : element.children()) {
            if (first && pattern.matcher(child.className()).matches()) {
                elements.add(clone);

                clone = element.shallowClone();
                clone.prependChildren(clone.children());
                clone.appendChild(child);

                append = false;
                continue;
            }

            clone.appendChild(child);

            first = true;
            append = true;
        }

        if (append) {
            elements.add(clone);
        }

        return elements;
    }


}
