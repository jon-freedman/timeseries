package com.github.jonfreedman.timeseries.calculation;

/**
 * Performs a calculation on a {@link com.github.jonfreedman.timeseries.TimeSeriesCollection} which is dependent on the time value of each observation
 *
 * @param <K> Key type
 * @param <T> Time value type
 * @param <V> Value type to perform calculations
 * @param <R> Result type
 * @author jon
 */
public interface TemporalCalculator<K extends Comparable<K>, T extends Comparable<? super T>, V, R> extends Calculator<K, R> {
    /**
     * Records a single observation
     *
     * @param key       Key value corresponding to observation
     * @param timeValue Time value corresponding to observation
     * @param value     Observation
     */
    void observation(final K key, final T timeValue, final V value);
}