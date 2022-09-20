package br.com.blecaute.sigaa.api.model.discipline;


import br.com.blecaute.sigaa.api.annotation.selector.MapSelector;
import br.com.blecaute.sigaa.api.annotation.selector.Selector;
import br.com.blecaute.sigaa.api.annotation.validator.ClassValidator;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Data @Setter(AccessLevel.NONE)
@MapSelector.Key("period")
public class Period {

    @Selector("td")
    @ClassValidator("(?!linhaPar|linhaImpar)")
    private String period;

    @MapSelector(Discipline.class)
    private Map<String, Discipline> disciplines;

    @Unmodifiable
    public Collection<Discipline> getDisciplines() {
        return Collections.unmodifiableCollection(disciplines.values());
    }

    public Optional<Discipline> getDiscipline(@NonNull String code) {
        return Optional.ofNullable(disciplines.get(code));
    }

}