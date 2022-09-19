package br.com.blecaute.sigaa.api.parser.impl;

import br.com.blecaute.sigaa.api.model.classroom.Lesson;
import br.com.blecaute.sigaa.api.parser.LessonParser;
import br.com.blecaute.sigaa.api.processor.Processor;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

public class LessonParserImpl implements LessonParser {

    @Override
    public Lesson parse(@NotNull Element element) {
        // #formAva\:j_id_jsp_2083335174_274\:0\:titulo
        //  #formAva\:j_id_jsp_2083335174_274\:0\:conteudo



        return Processor.process(new Lesson(), element);
    }

}
