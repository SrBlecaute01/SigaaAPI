package br.com.blecaute.sigaa.api.model.classroom.attachment;

import br.com.blecaute.sigaa.api.annotation.selector.Selector;

public class ReferenceAttachment extends Attachment {

    @Selector(value = "a", attr = "href")
    private String url;

    @Override
    public AttachmentType getType() {
        return AttachmentType.REFERENCE;
    }

}
