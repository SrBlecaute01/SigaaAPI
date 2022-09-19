package br.com.blecaute.sigaa.api.model.classroom;

import br.com.blecaute.sigaa.api.annotation.selector.CollectionSelector;
import br.com.blecaute.sigaa.api.annotation.selector.Selector;

@CollectionSelector.Key(".item")
public class Attachment {

    @Selector(value = "a")
    private String name;

    @Selector(value = "a", attribute = "onClick", exclusion = ".*'id':'|','key.*")
    private String object;

    public enum Type {

        FORUM,
        CONTENT,
        REFERENCE,
        VIDEO,
        FILE,
        TASK,
        QUESTIONNAIRE,
        UNKNOWN

    }

}
