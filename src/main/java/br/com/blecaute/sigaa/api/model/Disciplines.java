package br.com.blecaute.sigaa.api.model;

import br.com.blecaute.sigaa.api.annotation.selector.MapSelector;
import br.com.blecaute.sigaa.api.annotation.selector.Selector;
import br.com.blecaute.sigaa.api.annotation.validator.ClassValidator;
import lombok.*;

import java.util.Map;

@Data
@Setter(AccessLevel.NONE)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Disciplines {

    @MapSelector(
            value = Period.class,
            step = "(?!linhaPar|linhaImpar)",
            selector = "#j_id_jsp_1186832992_2 > table > tbody",
            child = false)
    private Map<String, Period> periods;

    @MapSelector.Key("period")
    @ToString
    public static class Period {

        @Selector("td")
        @ClassValidator("(?!linhaPar|linhaImpar)")
        private String period;

        @MapSelector(Discipline.class)
        private Map<String, Discipline> disciplines;
    }

}