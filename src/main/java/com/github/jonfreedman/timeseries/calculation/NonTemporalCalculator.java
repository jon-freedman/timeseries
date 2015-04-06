package com.github.jonfreedman.timeseries.calculation;

/**
 * @param <K> Key type
 * @param <V> Value type to perform calculations
 * @param <R> Result type
 * @author jon
 */
public interface NonTemporalCalculator<K extends Comparable<K>, V, R> extends Calculator<K, R> {
    void observation(final K key, final V value);
}
