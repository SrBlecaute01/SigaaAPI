package br.com.blecaute.sigaa.api.model;

import br.com.blecaute.sigaa.api.annotation.selector.CollectionSelector;
import br.com.blecaute.sigaa.api.annotation.selector.MapSelector;
import br.com.blecaute.sigaa.api.annotation.selector.Selector;
import br.com.blecaute.sigaa.api.annotation.validator.ClassValidator;
import lombok.*;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;

@Data @Setter(AccessLevel.NONE)
@NoArgsConstructor
public class Disciplines {

    @MapSelector(
            value = Period.class,
            step = "(?!linhaPar|linhaImpar)",
            selector = "#j_id_jsp_1186832992_2 > table > tbody",
            child = false)
    private Map<String, Period> periods;

    @Unmodifiable
    public Collection<Period> getPeriods() {
        return Collections.unmodifiableCollection(periods.values());
    }

    public Optional<Period> getPeriod(@NonNull String period) {
        return Optional.ofNullable(periods.get(period));
    }

    @Data @Setter(AccessLevel.NONE)
    @MapSelector.Key("period")
    public static class Period {

        @Selector("td")
        private String period;

        @MapSelector(Discipline.class)
        private Map<String, Discipline> disciplines;

        @Unmodifiable
        public Collection<Discipline> getDisciplines() {
            return Collections.unmodifiableCollection(disciplines.values());
        }

        @SneakyThrows
        public Optional<Discipline> getDiscipline(@NonNull String code) {
            return Optional.ofNullable(disciplines.get(code));
        }

    }

    @Data @Setter(AccessLevel.NONE)
    @NoArgsConstructor
    @MapSelector.Key("code")
    @ClassValidator("linhaPar|linhaImpar")
    public static class Discipline {

        @Selector(value = "td:nth-child(6) > a", attr = "onclick", exclusion = "(.*j_id_jsp_1186832992_2:)|('.*)")
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

}