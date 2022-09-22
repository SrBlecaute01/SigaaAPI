package br.com.blecaute.sigaa.api.model.classroom;

import br.com.blecaute.sigaa.api.annotation.selector.CollectionSelector;
import br.com.blecaute.sigaa.api.annotation.selector.DateSelector;
import br.com.blecaute.sigaa.api.annotation.selector.Selector;
import br.com.blecaute.sigaa.api.model.classroom.attachment.Attachment;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@ToString
@Setter(AccessLevel.NONE)
@NoArgsConstructor
@AllArgsConstructor
@CollectionSelector.Key(".topico-aula")
public class Lesson {

    @Selector(value = ".titulo", exclusion = "\s+\\(.*\\)$")
    private String title;

    @Selector(value = ".conteudotopico > p", first = false)
    private String content;

    @DateSelector(pattern = "dd/MM/yyyy", selector = @Selector(value = ".titulo", exclusion = ".*\\(|\\s+-\\s+.*"))
    private Date start;

    @DateSelector(pattern = "dd/MM/yyyy", selector = @Selector(value = ".titulo", exclusion = ".*-\\s+|\\)$"))
    private Date end;

    @CollectionSelector(".conteudotopico")
    private List<Attachment> attachments;

}