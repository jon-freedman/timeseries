package com.github.jonfreedman.timeseries.localdate;

import com.github.jonfreedman.timeseries.Traverser;

import java.time.LocalDate;

/**
 * @author jon
 */
public class LocalDateTraverser implements Traverser<LocalDate> {
    private LocalDate current;

    public LocalDateTraverser(final LocalDate initial) {
        current = initial;
    }

    @Override
    public LocalDate next() {
        current = current.plusDays(1);
        return current;
    }

    @Override
    public LocalDate skip(int n) {
        current = current.plusDays(1 + n);
        return current;
    }
}
