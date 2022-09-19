package br.com.blecaute.sigaa.api.parser.impl;

import br.com.blecaute.sigaa.api.processor.Processor;
import br.com.blecaute.sigaa.api.model.User;
import br.com.blecaute.sigaa.api.parser.UserParser;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

public class UserParserImpl implements UserParser {

    @Override
    public User parse(@NotNull Element element) {
        return Processor.process(new User(), element);
    }

}
