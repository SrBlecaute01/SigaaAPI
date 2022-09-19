package br.com.blecaute.sigaa.api.processor.impl;

import br.com.blecaute.sigaa.api.annotation.selector.ParserSelector;
import br.com.blecaute.sigaa.api.processor.Processor;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

import java.lang.reflect.Field;

public class ParserProcessorImpl implements Processor<ParserSelector> {

    @Override
    public void process(@NotNull Object object, @NotNull Field field, @NotNull ParserSelector parserSelector,
                        @NotNull Element document) {

    }

    @Override
    public Object parse(@NotNull Field field, @NotNull ParserSelector parserSelector, @NotNull Element document) {


        return null;
    }

}
