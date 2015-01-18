package com.github.jonfreedman.timeseries;

import java.util.SortedSet;

/**
 * @author jon
 */
public interface TimeSeriesCollection<K extends Comparable<K>, V> {
    public SortedSet<K> keySet();

    public TimeSeries<V> get(final K key);
}
