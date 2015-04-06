package com.github.jonfreedman.timeseries.calculation;

import java.util.Map;

/**
 * @param <K> Key type
 * @param <R> Result type
 * @author jon
 */
public interface Calculator<K extends Comparable<K>, R> {
    default R result(final K key) {
        return results().get(key);
    }

    Map<K, R> results();
}