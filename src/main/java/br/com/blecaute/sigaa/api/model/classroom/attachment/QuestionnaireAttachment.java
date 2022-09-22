package br.com.blecaute.sigaa.api.model.classroom.attachment;

import br.com.blecaute.sigaa.api.annotation.selector.Selector;

public class QuestionnaireAttachment extends Attachment {

    @Selector(value = "a", attr = "onClick", exclusion = ".*\\{'|':'.*")
    private String id;

    @Selector(value = "a", attr = "onClick", exclusion = ".*'id':'|','key.*|'},.*")
    private String object;

    @Override
    public AttachmentType getType() {
        return AttachmentType.QUESTIONNAIRE;
    }
}
