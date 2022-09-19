package br.com.blecaute.sigaa.api.parser.impl;

import br.com.blecaute.sigaa.api.model.Discipline;
import br.com.blecaute.sigaa.api.parser.DisciplineParser;
import br.com.blecaute.sigaa.api.processor.Processor;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

public class DisciplineParserImpl implements DisciplineParser {

    @Override
    public Discipline parse(@NotNull Element element) {
        final var discipline = Processor.process(new Discipline(), element);

        final var code = discipline.getCode();


        return null;
    }

}