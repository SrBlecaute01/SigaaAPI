package br.com.blecaute.sigaa.api.model;

import br.com.blecaute.sigaa.api.annotation.selector.CollectionSelector;

import java.time.DayOfWeek;
import java.util.List;

@CollectionSelector.Key("td:nth-child(5)")
public record Schedule(DayOfWeek dayOfWeek, DayPeriod dayPeriod, List<Integer> periods) {}