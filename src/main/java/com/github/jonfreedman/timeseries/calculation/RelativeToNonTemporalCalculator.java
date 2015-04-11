package com.github.jonfreedman.timeseries.calculation;

/**
 * @author jon
 */
public interface RelativeToNonTemporalCalculator<K extends Comparable<K>, V, R> extends NonTemporalCalculator<K, V, R> {
    void incrementRelative();
}
