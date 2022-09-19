package br.com.blecaute.sigaa.api.parser.impl;

import br.com.blecaute.sigaa.api.model.Bulletin;
import br.com.blecaute.sigaa.api.parser.BulletinParser;
import br.com.blecaute.sigaa.api.processor.Processor;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

public class BulletinParserImpl implements BulletinParser {

    @Override
    public Bulletin parse(@NotNull Element element) {
        return Processor.process(new Bulletin(), element);
    }

}