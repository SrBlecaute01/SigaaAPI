package br.com.blecaute.sigaa.api.parser.impl;

import br.com.blecaute.sigaa.api.model.classroom.attachment.Attachment;
import br.com.blecaute.sigaa.api.model.classroom.attachment.AttachmentType;
import br.com.blecaute.sigaa.api.parser.AttachmentParser;
import br.com.blecaute.sigaa.api.processor.Processor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

public class AttachmentParserImpl implements AttachmentParser {

    @Override @SneakyThrows
    public Attachment parse(@NotNull Element element) {
        final var type = AttachmentType.valueOf(element);
        if (type == AttachmentType.UNKNOWN) {
            System.out.println("Unknown attachment type: " + element);
            return null;
        }

        final var object = type.getClazz().getConstructor().newInstance();
        return Processor.process(object, element);
    }

}
