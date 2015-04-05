package com.github.jonfreedman.timeseries;

import java.util.Iterator;
import java.util.Map;

/**
 * @param <T> Time value type
 * @param <V> Value type
 * @author jon
 */
public interface TimeSeries<T extends Comparable<? super T>, V> {
    /**
     * @return Iterator which allows observation of values with their time value
     */
    Iterator<Map.Entry<T, V>> timeValueIterator();

    /**
     * @return Iterator which allows observation of values without their time value
     */
    Iterator<V> ordinalIterator();
}
