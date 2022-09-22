package br.com.blecaute.sigaa.api.mapper.impl;

import br.com.blecaute.sigaa.api.model.DayPeriod;
import br.com.blecaute.sigaa.api.model.Schedule;
import br.com.blecaute.sigaa.api.mapper.ScheduleMapper;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Element;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class ScheduleMapperImpl implements ScheduleMapper {

    @Override
    public Schedule map(@NotNull Element element) {
        String[] values = element.text().split(" ");
        for (String value : values) {
            DayOfWeek day = DayOfWeek.of(Integer.parseInt(String.valueOf(value.charAt(0))));
            DayPeriod shift = DayPeriod.of(String.valueOf(value.charAt(1)));
            List<Integer> periods = new ArrayList<>();

            for (char character : value.substring(2).toCharArray()) {
                periods.add(Integer.parseInt(String.valueOf(character)));
            }

            return new Schedule(day, shift, periods);
        }

        return null;
    }

}