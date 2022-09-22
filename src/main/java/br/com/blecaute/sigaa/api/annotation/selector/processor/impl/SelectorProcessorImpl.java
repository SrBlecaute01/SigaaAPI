package br.com.blecaute.sigaa.api.annotation.selector.processor.impl;

import br.com.blecaute.sigaa.api.annotation.selector.Selector;
import br.com.blecaute.sigaa.api.annotation.selector.processor.Processor;
import br.com.blecaute.sigaa.api.annotation.validator.Validators;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.nodes.Element;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class SelectorProcessorImpl implements Processor<Selector> {

    private final Map<String, Element> parents = new HashMap<>();

    @Override @SneakyThrows
    public void process(@NotNull Object object, @NotNull Field field, @NotNull Selector selector, @NotNull Element document) {
        final var value = parse(field, selector, document);
        if (value != null) {
            field.set(object, value);
        }
    }

    @Override
    public Object parse(@NotNull Field field, @NotNull Selector selector, @NotNull Element document) {
        document = Objects.requireNonNullElse(findParent(selector, document), document);

        var value = "";
        if (selector.first()) {
            final var element = document.selectFirst(selector.value());
            if (element == null || !Validators.validate(field, element)) return null;

            if (selector.attr().isBlank()) {
                value = selector.ownText() ? element.ownText() : element.text();

            } else {
                value = element.attr(selector.attr());
            }

        } else {
            final var elements = document.select(selector.value());
            if (elements.isEmpty() || !Validators.validate(field, elements)) return null;

            value = selector.attr().isBlank() ? elements.text() : elements.attr(selector.attr());
        }

        if (!selector.exclusion().isBlank()) {
            value = value.replaceAll(selector.exclusion(), "");
        }

        final var type = field.getType();
        if (type.isEnum()) {
            return parseEnum(type, value);
        }

        if (type.isPrimitive()) {
            return Primitive.parse(type, value);
        }

        return value;
    }

    @Nullable
    private Element findParent(@NotNull Selector selector, @NotNull Element element) {
        Element parent = null;
        if (!selector.parent().isBlank()) {
            parent = parents.get(selector.parent());
            if (parent == null) {
                parent = element.selectFirst(selector.parent());
                if (parent != null) {
                    parents.put(selector.parent(), element);
                }
            }
        }

        return parent;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Object parseEnum(Class<?> clazz, String value) {
        try {
            Method method = clazz.getMethod("parse", String.class);
            method.setAccessible(true);

            return method.invoke(null, value);

        } catch (Exception exception) {
            return Enum.valueOf((Class<Enum>) clazz, value);
        }
    }

    @RequiredArgsConstructor @Getter
    private enum Primitive {

        BYTE(byte.class, Byte.class, text -> parseNumber(text, Byte::parseByte)),
        SHORT(short.class, Short.class, text -> parseNumber(text, Short::parseShort)),
        INTEGER(int.class, Integer.class, text -> parseNumber(text, Integer::parseInt)),
        LONG(long.class, Long.class, text -> parseNumber(text, Long::parseLong)),
        FLOAT(float.class, Float.class, text -> parseNumber(text, Float::parseFloat)),
        DOUBLE(double.class, Double.class, text -> parseNumber(text, Double::parseDouble)),
        BOOLEAN(boolean.class, Boolean.class, Boolean::parseBoolean);

        private final Class<?> primitive;
        private final Class<?> reference;

        private final Function<String, Object> function;

        @Nullable
        public static Primitive fromClass(@NotNull Class<?> clazz) {
            for (Primitive primitive : Primitive.values()) {
                if (primitive.getPrimitive() == clazz ||
                        primitive.getReference() == clazz) {

                    return primitive;
                }
            }

            return null;
        }

        @Nullable
        public static Object parse(@NotNull Class<?> clazz, @NotNull String value) {
            Primitive primitive = fromClass(clazz);
            return primitive == null ? null : primitive.function.apply(value);
        }

        private static Number parseNumber(String text, Function<String, Number> function) {
            try {
                return function.apply(text.replace(",", "."));
            } catch (Exception exception) {
                return -1;
            }
        }

    }

}
