package br.com.blecaute.sigaa.api.model.classroom.attachment;

import br.com.blecaute.sigaa.api.annotation.selector.Selector;

public class ForumAttachment extends Attachment {

    @Selector(value = "a", attr = "onClick", exclusion = ".*\\{'|':'.*")
    private String id;

    @Override
    public AttachmentType getType() {
        return AttachmentType.FORUM;
    }
}