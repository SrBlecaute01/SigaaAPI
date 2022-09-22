package br.com.blecaute.sigaa.api.model.classroom.attachment;

import br.com.blecaute.sigaa.api.annotation.selector.CollectionSelector;
import br.com.blecaute.sigaa.api.annotation.selector.Selector;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data @Setter(AccessLevel.NONE)
@CollectionSelector.Key(".item")
public abstract class Attachment {

    @Selector(value = "a")
    private String name;

    @Selector(value = "img", attr = "src")
    private String image;

    @Selector(".descricao-item")
    private String description;

    public abstract AttachmentType getType();

}