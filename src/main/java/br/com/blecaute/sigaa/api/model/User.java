package br.com.blecaute.sigaa.api.model;

import br.com.blecaute.sigaa.api.annotation.selector.Selector;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Data
@NoArgsConstructor
@Setter(AccessLevel.NONE)
public class User {

    @Selector(value = "#agenda-docente > table > tbody > tr:nth-child(1) > td:nth-child(2)")
    private int registration;

    @Selector(value = "#perfil-docente > p.info-docente > span")
    private String name;

    @Selector(value = "#perfil-docente > p.info-docente")
    private String info;

    @Selector(value = "#perfil-docente > div.pessoal-docente > div.foto > img", attr = "src")
    private String avatar;

    @Selector("#info-usuario > p.periodo-atual > strong")
    private String semester;

    @Selector(value = "#agenda-docente > table > tbody > tr:nth-child(2) > td:nth-child(2)")
    private String course;

    @Selector(value = "#agenda-docente > table > tbody > tr:nth-child(4) > td:nth-child(2)")
    private Status status;

    @Selector(value = "#agenda-docente > table > tbody > tr:nth-child(6) > td:nth-child(2)")
    private String entryYear;

    @RequiredArgsConstructor
    public enum Status {

        ACTIVE("ATIVO");

        private final String value;

        @Nullable
        public static Status parse(@NotNull String status) {
            for (Status type : Status.values()) {
                if (type.value.equalsIgnoreCase(status)) {
                    return type;
                }
            }

            return null;
        }

    }

}
