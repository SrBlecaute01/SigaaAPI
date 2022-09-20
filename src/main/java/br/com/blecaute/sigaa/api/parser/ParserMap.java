package br.com.blecaute.sigaa.api.parser;

import br.com.blecaute.sigaa.api.model.*;
import br.com.blecaute.sigaa.api.model.classroom.Attachment;
import br.com.blecaute.sigaa.api.model.classroom.Classroom;
import br.com.blecaute.sigaa.api.model.classroom.Lesson;
import br.com.blecaute.sigaa.api.parser.impl.*;
import br.com.blecaute.sigaa.api.processor.Processor;
import lombok.SneakyThrows;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.Map;

public class ParserMap {

    private static final Map<Class<?>, Parser<?>> parsers = new HashMap<>();

    static {
        parsers.put(User.class, new UserParserImpl());
        parsers.put(Schedule.class, new ScheduleParserImpl());
        parsers.put(Bulletin.class, new BulletinParserImpl());
        parsers.put(Lesson.class, new LessonParserImpl());
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
