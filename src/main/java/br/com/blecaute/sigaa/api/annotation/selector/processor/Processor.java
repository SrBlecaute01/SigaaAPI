package br.com.blecaute.sigaa.api.annotation.selector.processor;

import lombok.NonNull;
import org.jsoup.nodes.Element;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public interface Processor<T extends Annotation> {

    Object process(@NonNull Field field, @NonNull T annotation, @NonNull Element element);

}