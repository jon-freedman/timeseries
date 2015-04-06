package com.github.jonfreedman.timeseries.calculation;

/**
 * Performs a calculation on a {@link com.github.jonfreedman.timeseries.TimeSeriesCollection} which is independent of the time value of each observation
 *
 * @param <K> Key type
 * @param <V> Value type to perform calculations
 * @param <R> Result type
 * @author jon
 */
public interface NonTemporalCalculator<K extends Comparable<K>, V, R> extends Calculator<K, R> {
    /**
     * Records a single observation
     *
     * @param key   Key value corresponding to observation
     * @param value Observation
     */
    void observation(final K key, final V value);
}
