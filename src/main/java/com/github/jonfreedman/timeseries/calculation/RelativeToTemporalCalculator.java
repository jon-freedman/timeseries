package com.github.jonfreedman.timeseries.calculation;

/**
 * @author jon
 */
public interface RelativeToTemporalCalculator<K extends Comparable<K>, T extends Comparable<? super T>, V, R> extends TemporalCalculator {
    void incrementRelative(final T timeValue);
}
