package br.com.blecaute.sigaa.api.mapper;

import br.com.blecaute.sigaa.api.annotation.selector.CollectionSelector;
import br.com.blecaute.sigaa.api.annotation.selector.DateSelector;
import br.com.blecaute.sigaa.api.annotation.selector.MapSelector;
import br.com.blecaute.sigaa.api.annotation.selector.Selector;
import br.com.blecaute.sigaa.api.annotation.selector.processor.Processor;
import br.com.blecaute.sigaa.api.annotation.selector.processor.impl.CollectionProcessorImpl;
import br.com.blecaute.sigaa.api.annotation.selector.processor.impl.DateProcessorImpl;
import br.com.blecaute.sigaa.api.annotation.selector.processor.impl.MapProcessorImpl;
import br.com.blecaute.sigaa.api.annotation.selector.processor.impl.SelectorProcessorImpl;
import br.com.blecaute.sigaa.api.mapper.impl.AttachmentMapperImpl;
import br.com.blecaute.sigaa.api.mapper.impl.ScheduleMapperImpl;
import br.com.blecaute.sigaa.api.model.Schedule;
import br.com.blecaute.sigaa.api.model.classroom.attachment.Attachment;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.nodes.Element;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Class responsible for mapping the data from the HTML document to the object.
 */
public class Mappers {

    private static final Map<Class<?>, Mapper<?>> mappers = new HashMap<>();
    private static final Map<Class<? extends Annotation>, Processor<Annotation>> processors = new LinkedHashMap<>();
    private static final Map<Class<?>, List<Field>> fields = new HashMap<>();

    static {
        registerMapper(Attachment.class, new AttachmentMapperImpl());
        registerMapper(Schedule.class, new ScheduleMapperImpl());

        registerProcessor(Selector.class, new SelectorProcessorImpl());
        registerProcessor(DateSelector.class, new DateProcessorImpl());
        registerProcessor(MapSelector.class, new MapProcessorImpl());
        registerProcessor(CollectionSelector.class, new CollectionProcessorImpl());
    }

    /**
     * Register a new mapper.
     *
     * @param clazz The class.
     * @param mapper The mapper.
     *
     * @param <T> The class type.
     */
    public static <T> void registerMapper(@NonNull Class<T> clazz, @NonNull Mapper<T> mapper) {
        if (mappers.containsKey(clazz)) {
            throw new IllegalArgumentException("Mapper already registered for class " + clazz.getName());
        }

        mappers.put(clazz, mapper);
    }

    /**
     * Unregister a mapper.
     *
     * @param clazz The class.
     */
    public static void unregisterMapper(@NonNull Class<?> clazz) {
        mappers.remove(clazz);
    }

    /**
     * Register a new processor.
     *
     * @param annotation The annotation.
     * @param processor The processor.
     *
     * @param <T> The annotation type.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Annotation> void registerProcessor(@NonNull Class<T> annotation,
                                                                @NonNull Processor<T> processor) {

        if (processors.containsKey(annotation)) {
            throw new IllegalArgumentException("Processor already registered for annotation " + annotation.getName());
        }

        processors.put(annotation, (Processor<Annotation>) processor);
    }

    /**
     * Unregister a processor.
     * @param annotation The annotation.
     */
    public static void unregisterProcessor(@NonNull Class<? extends Annotation> annotation) {
        processors.remove(annotation);
    }

    /**
     * Get processor
     *
     * @param annotation The annotation.
     *
     * @return The processor of the annotation.
     *
     * @param <T> The annotation type.
     */
    @Nullable @SuppressWarnings("unchecked")
    public static <T extends Annotation> Processor<T> getProcessor(@NonNull Class<T> annotation) {
        return (Processor<T>) processors.get(annotation);
    }

    /**
     * Map class with given @{@link Element}
     *
     * @param clazz The class.
     * @param element The element.
     *
     * @return The mapped object.
     *
     * @param <T> The class type.
     */
    @SneakyThrows
    public static <T> T map(@NonNull Class<T> clazz, @NonNull Element element) {
        final var mapper = mappers.get(clazz);
        if (mapper != null) return clazz.cast(mapper.map(element));

        final var object = clazz.getConstructor().newInstance();
        final var set = processors.entrySet();

        getFields(clazz).forEach(field -> set.forEach(entry -> {
            final var annotation = field.getAnnotation(entry.getKey());
            if (annotation == null) return;

            final var value = entry.getValue().process(field, annotation, element);
            if (value == null) return;

            try {
                field.set(object, value);

            } catch (IllegalAccessException exception) {
                exception.printStackTrace();
            }
        }));

        return object;
    }

    private static List<Field> getFields(@NotNull Class<?> clazz) {
        if (fields.containsKey(clazz)) {
            return fields.get(clazz);
        }

        final var list = new ArrayList<Field>();

        var current = clazz;
        while (current != null) {
            for (Field field : current.getDeclaredFields()) {
                field.setAccessible(true);
                list.add(field);
            }

            current = current.getSuperclass();
        }

        fields.put(clazz, list);
        return list;
    }

}