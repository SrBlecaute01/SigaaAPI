package br.com.blecaute.sigaa.api.model.classroom;

import br.com.blecaute.sigaa.api.annotation.selector.CollectionSelector;
import br.com.blecaute.sigaa.api.annotation.selector.Selector;
import br.com.blecaute.sigaa.api.model.DayPeriod;
import lombok.*;

import java.util.List;

@Data @Setter(AccessLevel.NONE)
@NoArgsConstructor
public class Classroom {

    @Selector(value = "#linkCodigoTurma", exclusion = "\\W+")
    private String code;

    @Selector("#linkNomeTurma")
    private String name;

    @Selector(value = "#linkPeriodoTurma", exclusion = "\\(|\\)|-.*|\\s+")
    private String period;

    @Selector(value = "#linkPeriodoTurma", exclusion = ".*-\\s+|\\s+.*")
    private String course;

    @Selector(value = "#linkPeriodoTurma", exclusion = ".*-|\\D+")
    private int grade;

    @Selector(value = "#linkPeriodoTurma", exclusion = "\\W+|.*\\s+")
    private DayPeriod dayPeriod;

    @CollectionSelector("#formAva > span")
    private List<Lesson> lessons;

}