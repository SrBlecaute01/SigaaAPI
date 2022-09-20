package br.com.blecaute.sigaa.api.model.discipline;

import br.com.blecaute.sigaa.api.annotation.selector.MapSelector;
import lombok.*;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

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

}