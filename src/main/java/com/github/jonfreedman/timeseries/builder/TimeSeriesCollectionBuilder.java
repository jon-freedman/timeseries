package com.github.jonfreedman.timeseries.builder;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * As TimeSeriesCollection instances are immutable use a TimeSeriesCollectionBuilder to load data and construct an instance
 *
 * @author jon
 */
public final class TimeSeriesCollectionBuilder<K extends Comparable<K>, T extends Comparable<T>, V> {
    private final ConcurrentMap<K, ConcurrentMap<T, V>> state = new ConcurrentHashMap<>();

    public TimeSeriesCollectionBuilder<K, T, V> addValue(K key, T x, V y) {
        ConcurrentMap<T, V> values = state.get(key);
        if (values == null) {
            values = state.putIfAbsent(key, new ConcurrentHashMap<>());
            if (values == null) {
                values = state.get(key);
            }
        }
        values.put(x, y);
        return this;
    }
}