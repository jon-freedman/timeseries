package com.github.jonfreedman.timeseries.calculation;

import java.util.Map;

/**
 * Performs a calculation on a {@link com.github.jonfreedman.timeseries.TimeSeriesCollection}
 *
 * @param <K> Key type
 * @param <R> Result type
 * @author jon
 */
public interface Calculator<K extends Comparable<K>, R> {
    /**
     * @param key Single key value
     * @return Result for specific key value
     */
    default R result(final K key) {
        return results().get(key);
    }

    /**
     * @return All results indexed by key value
     */
    Map<K, R> results();
}