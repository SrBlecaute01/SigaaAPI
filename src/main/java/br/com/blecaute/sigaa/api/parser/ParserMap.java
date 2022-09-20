package br.com.blecaute.sigaa.api.parser;

import br.com.blecaute.sigaa.api.model.Bulletin;
import br.com.blecaute.sigaa.api.model.Discipline;
import br.com.blecaute.sigaa.api.model.classroom.Attachment;
import br.com.blecaute.sigaa.api.model.classroom.Classroom;
import br.com.blecaute.sigaa.api.model.User;
import br.com.blecaute.sigaa.api.model.Schedule;
import br.com.blecaute.sigaa.api.model.classroom.Lesson;
import br.com.blecaute.sigaa.api.parser.impl.*;
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
        parsers.put(Discipline.class, new DisciplineParserImpl());
    }

    public static <T> T parse(Class<T> clazz, Element element) {
        final var parser= parsers.get(clazz);
        if (parser == null) {
            throw new IllegalArgumentException("could not find parser for class " + clazz.getSimpleName());
        }

        return clazz.cast(parser.parse(element));
    }

}
