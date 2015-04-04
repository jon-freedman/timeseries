package com.github.jonfreedman.timeseries;

import java.util.SortedSet;

/**
 * @author jon
 */
public interface TimeSeriesCollection<K extends Comparable<K>, T extends Comparable<T>, V> {
    public SortedSet<K> keySet();

    public TimeSeries<T, V> get(final K key);
}
