package com.github.jonfreedman.timeseries;

import java.util.Map;
import java.util.SortedSet;

/**
 * @author jon
 */
public interface TimeSeriesCollection<K extends Comparable<K>, T extends Comparable<? super T>, V> extends Iterable<Map.Entry<K, TimeSeries<T, V>>> {
    SortedSet<K> keySet();

    T minValue();

    T maxValue();

    TimeSeries<T, V> get(final K key);
}