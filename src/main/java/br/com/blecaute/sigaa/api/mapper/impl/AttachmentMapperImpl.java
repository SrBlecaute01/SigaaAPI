package br.com.blecaute.sigaa.api.mapper.impl;

import br.com.blecaute.sigaa.api.mapper.Mappers;
import br.com.blecaute.sigaa.api.model.classroom.attachment.Attachment;
import br.com.blecaute.sigaa.api.model.classroom.attachment.AttachmentType;
import br.com.blecaute.sigaa.api.mapper.AttachmentMapper;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

public class AttachmentMapperImpl implements AttachmentMapper {

    @Override
    public Attachment map(@NotNull Element element) {
        final var type = AttachmentType.valueOf(element);
        if (type == AttachmentType.UNKNOWN) {
            System.out.println("Unknown attachment type: " + element);
            return null;
        }

        return Mappers.map(type.getClazz(), element);
    }

}
