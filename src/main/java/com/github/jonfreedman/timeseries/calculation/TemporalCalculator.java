package com.github.jonfreedman.timeseries.calculation;

/**
 * @param <K> Key type
 * @param <T> Time value type
 * @param <V> Value type to perform calculations
 * @param <R> Result type
 * @author jon
 */
public interface TemporalCalculator<K extends Comparable<K>, T extends Comparable<? super T>, V, R> extends Calculator<K, R> {
    void observation(final K key, final T timeValue, final V value);
}