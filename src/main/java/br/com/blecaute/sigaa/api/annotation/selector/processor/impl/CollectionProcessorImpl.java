package br.com.blecaute.sigaa.api.annotation.selector.processor.impl;

import br.com.blecaute.sigaa.api.annotation.selector.CollectionSelector;
import br.com.blecaute.sigaa.api.annotation.selector.processor.Processor;
import br.com.blecaute.sigaa.api.mapper.Mappers;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jsoup.nodes.Element;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

public class CollectionProcessorImpl implements Processor<CollectionSelector> {

    @Override @SuppressWarnings("unchecked")
    public Object process(@NonNull Field field, @NonNull CollectionSelector collectionSelector, @NonNull Element document) {
        if (!Collection.class.isAssignableFrom(field.getType())) {
            throw new IllegalArgumentException("The field must be a collection");
        }

        final var collection = (Collection<Object>) CollectionType.newInstance(field.getType());
        final var element = collectionSelector.value().isBlank() ? document : document.selectFirst(collectionSelector.value());

        if (element == null) return collection;

        final var parameterized = (ParameterizedType) field.getGenericType();
        final var type = (Class<?>) parameterized.getActualTypeArguments()[0];
        final var annotation = type.getAnnotation(CollectionSelector.Key.class);

        if (annotation == null) {
            throw new IllegalArgumentException("The key of collection selector must be present");
        }

        for (Element child : element.children()) {
            child = child.selectFirst(annotation.value());
            if (child != null) {
                collection.add(Mappers.map(type, child));
            }
        }

        return collection;
    }

    @RequiredArgsConstructor
    public enum CollectionType {

        LIST(List.class, ArrayList.class),
        SET(Set.class, HashSet.class),
        QUEUE(Queue.class, LinkedList.class),
        COLLECTION(Collection.class, ArrayList.class);

        private final Class<?> clazz;
        private final Class<?> map;

        private static final CollectionType[] values = values();

        @SneakyThrows
        public static Collection<?> newInstance(Class<?> clazz) {
            for (CollectionType type : values) {
                if (type.clazz.isAssignableFrom(clazz)) {
                    return (Collection<?>) type.map.getConstructor().newInstance();
                }
            }

            return new ArrayList<>();
        }

    }


}