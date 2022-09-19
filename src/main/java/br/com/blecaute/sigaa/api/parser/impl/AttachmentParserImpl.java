package br.com.blecaute.sigaa.api.parser.impl;

import br.com.blecaute.sigaa.api.model.classroom.Attachment;
import br.com.blecaute.sigaa.api.parser.AttachmentParser;
import br.com.blecaute.sigaa.api.processor.Processor;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

public class AttachmentParserImpl implements AttachmentParser {

    @Override
    public Attachment parse(@NotNull Element element) {
        return Processor.process(new Attachment(), element);
    }

}
