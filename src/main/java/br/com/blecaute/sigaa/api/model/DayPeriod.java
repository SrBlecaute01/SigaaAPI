package br.com.blecaute.sigaa.api.model;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public enum DayPeriod {

    MORNING("M", "MAT"),
    EVENING("V", "VES"),
    NIGHT("N", "NOT");

    private final List<String> names;
    private static final DayPeriod[] values = DayPeriod.values();

    DayPeriod(String... names) {
        this.names = Arrays.asList(names);
    }

    @Nullable
    public static DayPeriod of(String input) {
        for (DayPeriod shift : values) {
            if (shift.name().equals(input) || shift.names.contains(input)) {
                return shift;
            }
        }

        return null;
    }

    @Nullable
    public static DayPeriod parse(String input) {
        return DayPeriod.of(input);
    }

}
