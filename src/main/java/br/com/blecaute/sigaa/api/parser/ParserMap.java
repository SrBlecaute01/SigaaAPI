package br.com.blecaute.sigaa.api.parser;

import br.com.blecaute.sigaa.api.model.*;
import br.com.blecaute.sigaa.api.model.classroom.attachment.Attachment;
import br.com.blecaute.sigaa.api.parser.impl.*;
import br.com.blecaute.sigaa.api.annotation.selector.processor.Processor;
import lombok.SneakyThrows;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.Map;

public class ParserMap {

    private static final Map<Class<?>, Parser<?>> parsers = new HashMap<>();

    static {
        parsers.put(Schedule.class, new ScheduleParserImpl());
        parsers.put(Attachment.class, new AttachmentParserImpl());
    }

    @SneakyThrows
    public static <T> T parse(Class<T> clazz, Element element) {
        final var parser= parsers.get(clazz);
        if (parser == null) {
            T object = clazz.getConstructor().newInstance();
            return Processor.process(object, element);
        }

        return clazz.cast(parser.parse(element));
    }

}
