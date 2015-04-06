package com.github.jonfreedman.timeseries;

import com.github.jonfreedman.timeseries.calculation.NonTemporalCalculator;
import com.github.jonfreedman.timeseries.calculation.TemporalCalculator;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.SortedSet;

/**
 * @param <K> Key type
 * @param <T> Time value type
 * @param <V> Value type
 * @author jon
 */
public interface TimeSeriesCollection<K extends Comparable<K>, T extends Comparable<? super T>, V> extends Iterable<Map.Entry<K, TimeSeries<T, V>>> {
    SortedSet<K> keySet();

    T minValue();

    T maxValue();

    TimeSeries<T, V> get(final K key);

    default void calculateNonTemporal(final Collection<NonTemporalCalculator<K, ? super V, ?>> nonTemporalCalculators) {
        calculate(nonTemporalCalculators, Collections.emptyList());
    }

    default void calculateTemporal(final Collection<TemporalCalculator<K, T, ? super V, ?>> temporalCalculators) {
        calculate(Collections.emptyList(), temporalCalculators);
    }

    void calculate(final Collection<NonTemporalCalculator<K, ? super V, ?>> nonTemporalCalculators, final Collection<TemporalCalculator<K, T, ? super V, ?>> temporalCalculators);


}