package br.com.blecaute.sigaa.api.model.discipline;

import br.com.blecaute.sigaa.api.annotation.validator.ClassValidator;
import br.com.blecaute.sigaa.api.annotation.selector.CollectionSelector;
import br.com.blecaute.sigaa.api.annotation.selector.MapSelector;
import br.com.blecaute.sigaa.api.annotation.selector.Selector;
import br.com.blecaute.sigaa.api.model.DayPeriod;
import br.com.blecaute.sigaa.api.model.Schedule;
import lombok.*;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.List;

@Data @Setter(AccessLevel.NONE)
@NoArgsConstructor
@MapSelector.Key("code")
@ClassValidator("linhaPar|linhaImpar")
public class Discipline {

    @Selector(value = "td:nth-child(6) > a", attribute = "onclick", exclusion = "(.*j_id_jsp_1186832992_2:)|('.*)")
    private String id;

    @Selector(value = "td:nth-child(1)", exclusion = "\\s.*")
    private String code;

    @Selector(value = "td:nth-child(1)", exclusion = ".*-\\s")
    private String name;

    @Selector(value = "td:nth-child(2)", exclusion = "\\s+.*")
    private String course;

    @Selector(value = "td:nth-child(2)", exclusion = "[^(\\d+)]")
    private int grade;

    @Selector(value = "td:nth-child(2)", exclusion = ".*\\s+")
    private DayPeriod dayPeriod;

    @Selector("td:nth-child(3)")
    private int students;

    @Selector("td:nth-child(4)")
    private String workload;

    @CollectionSelector(value = "")
    private List<Schedule> schedules;

    @Unmodifiable
    public List<Schedule> getSchedules() {
        return Collections.unmodifiableList(schedules);
    }
}