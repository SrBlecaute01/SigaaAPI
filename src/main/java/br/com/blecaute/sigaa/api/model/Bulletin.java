package br.com.blecaute.sigaa.api.model;

import br.com.blecaute.sigaa.api.annotation.selector.DateSelector;
import br.com.blecaute.sigaa.api.annotation.selector.MapSelector;
import br.com.blecaute.sigaa.api.annotation.selector.Selector;
import lombok.*;

import java.util.Date;
import java.util.Map;

@Data
@Setter(AccessLevel.NONE)
@AllArgsConstructor
@NoArgsConstructor
public class Bulletin {

    @DateSelector(
            pattern = "'Emitido em' dd/MM/yyyy HH:mm",
            selector = @Selector("#texto > span"))
    private Date issuedOn;

    @MapSelector(
            value = Period.class,
            selector = "#relatorio > div")
    private Map<String, Period> periods;

    @Data
    @Setter(AccessLevel.NONE)
    @NoArgsConstructor
    @AllArgsConstructor
    @MapSelector.Key("period")
    public static class Period {

        @Selector("caption")
        private String period;

        @MapSelector(value = DisciplineGrades.class, selector = "tbody")
        private Map<String, DisciplineGrades> grades;

    }

    @Data
    @Setter(AccessLevel.NONE)
    @NoArgsConstructor
    @AllArgsConstructor
    @MapSelector.Key("td:nth-child(1)")
    public static class DisciplineGrades {

        @Selector("td:nth-child(1)")
        private String code;

        @Selector("td:nth-child(2)")
        private String discipline;

        @Selector("td:nth-child(3)")
        private float unit1;

        @Selector("td:nth-child(4)")
        private float unit2;

        @Selector("td:nth-child(5)")
        private float semesterRecovery1;

        @Selector("td:nth-child(6)")
        private float unit3;

        @Selector("td:nth-child(7)")
        private float unit4;

        @Selector("td:nth-child(8)")
        private float semesterRecovery2;

        @Selector("td:nth-child(9)")
        private float finalRecovery;

        @Selector("td:nth-child(10)")
        private float finalResult;

        @Selector("td:nth-child(11)")
        private int misses;

        @Selector("td.situacao")
        private Situation situation;

    }

    @RequiredArgsConstructor @Getter
    public enum Situation {

        APPROVED("APROVADO");

        private final String value;

        public static Situation parse(String value) {
            for (Situation situation : Situation.values()) {
                if (situation.value.equalsIgnoreCase(value)) {
                    return situation;
                }
            }

            return null;
        }

    }

}
