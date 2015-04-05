package com.github.jonfreedman.timeseries;

import java.util.Map;
import java.util.SortedSet;

/**
 * @author jon
 */
public interface TimeSeriesCollection<K extends Comparable<K>, T extends Comparable<? super T>, V> extends Iterable<Map.Entry<K, TimeSeries<T, V>>> {
    public SortedSet<K> keySet();

    public T minValue();

    public T maxValue();

    public TimeSeries<T, V> get(final K key);
}