package com.github.jonfreedman.timeseries.localdate;

import com.github.jonfreedman.timeseries.Traverser;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.Function;

import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoUnit.DAYS;

/**
 * @author jon
 */
public class WeekdayLocalDateTraverser implements Traverser<LocalDate> {
    private static final EnumSet<DayOfWeek> WESTERN_WEEKEND = EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

    private final EnumSet<DayOfWeek> weekend;
    private final Adjuster weekendAdjuster;
    private final int numberOfWeekdays;

    private LocalDate current;

    public WeekdayLocalDateTraverser(final LocalDate initial) {
        this(initial, WESTERN_WEEKEND);
    }

    public WeekdayLocalDateTraverser(final LocalDate initial, final EnumSet<DayOfWeek> weekend) {
        this.current = initial;
        this.weekend = weekend;
        this.weekendAdjuster = new Adjuster(weekend);
        this.numberOfWeekdays = 7 - weekend.size();
    }

    @Override
    public boolean canStartFrom(final LocalDate initialValue) {
        return !weekend.contains(initialValue.getDayOfWeek());
    }

    @Override
    public LocalDate next() {
        current = current.with(weekendAdjuster);
        return current;
    }

    @Override
    public LocalDate skip(final int n) {
        final int remainder = (n + 1) % numberOfWeekdays;
        LocalDate rolled = current;
        for (int i = 0; i < remainder; ++i) {
            rolled = rolled.with(weekendAdjuster);
        }
        final int fullWeeks = (n + 1) / numberOfWeekdays;
        current = rolled.plusDays(fullWeeks * 7);
        return current;
    }

    public static Function<LocalDate, Traverser<LocalDate>> factory() {
        return WeekdayLocalDateTraverser::new;
    }

    private static class Adjuster implements TemporalAdjuster {
        private final EnumMap<DayOfWeek, Integer> skips;

        public Adjuster(final Set<DayOfWeek> weekend) {
            final EnumMap<DayOfWeek, Integer> skips = new EnumMap<>(DayOfWeek.class);
            for (final DayOfWeek dow : DayOfWeek.values()) {
                int skip = 0;
                DayOfWeek skipped = dow;
                do {
                    ++skip;
                    skipped = skipped.plus(1);
                } while (weekend.contains(skipped));
                if (skip > 1) {
                    skips.put(dow, skip);
                }
            }
            this.skips = skips;
        }

        @Override
        public Temporal adjustInto(Temporal temporal) {
            final DayOfWeek dow = DayOfWeek.of(temporal.get(DAY_OF_WEEK));
            return temporal.plus(skips.getOrDefault(dow, 1), DAYS);
        }
    }
}