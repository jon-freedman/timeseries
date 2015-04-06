package com.github.jonfreedman.timeseries.localdate;

import com.github.jonfreedman.timeseries.Traverser;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoUnit.DAYS;

/**
 * @author jon
 */
public class WeekdayLocalDateTraverser implements Traverser<LocalDate> {
    private static final Set<DayOfWeek> WESTERN_WEEKEND = new HashSet<>();

    static {
        WESTERN_WEEKEND.add(DayOfWeek.SATURDAY);
        WESTERN_WEEKEND.add(DayOfWeek.SUNDAY);
    }

    private final Adjuster weekendAdjuster;
    private final int weekendLength;

    private LocalDate current;

    public WeekdayLocalDateTraverser(final LocalDate initial) {
        this(initial, WESTERN_WEEKEND);
    }

    public WeekdayLocalDateTraverser(final LocalDate initial, final Set<DayOfWeek> weekend) {
        current = initial;
        weekendAdjuster = new Adjuster(weekend);
        weekendLength = weekend.size();
    }

    @Override
    public LocalDate next() {
        current = current.with(weekendAdjuster);
        return current;
    }

    @Override
    public LocalDate skip(int n) {
        final int weekends = (int) Math.ceil(n / (7d - weekendLength));
        current = current.plusDays(n + (weekends * weekendLength)).with(weekendAdjuster);
        return current;
    }

    public static Function<LocalDate, Traverser<LocalDate>> factory() {
        return WeekdayLocalDateTraverser::new;
    }

    private static class Adjuster implements TemporalAdjuster {
        private final Map<DayOfWeek, Integer> skips;

        public Adjuster(final Set<DayOfWeek> weekend) {
            final Map<DayOfWeek, Integer> skips = new HashMap<>();
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