package com.github.jonfreedman.timeseries;

import com.github.jonfreedman.timeseries.calculation.NonTemporalCalculator;
import com.github.jonfreedman.timeseries.calculation.TemporalCalculator;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.SortedSet;
import java.util.function.Predicate;

/**
 * @param <K> Key type
 * @param <T> Time value type
 * @param <V> Value type
 * @author jon
 */
public interface TimeSeriesCollection<K extends Comparable<K>, T extends Comparable<? super T>, V> extends Iterable<Map.Entry<K, TimeSeries<T, V>>> {
    /**
     * @return Key values present
     */
    SortedSet<K> keySet();

    /**
     * @param filter Filter to apply to keys
     * @return New collection with filter of keys based on predicate, the {@link #length()} of the new collection is the same
     */
    TimeSeriesCollection<K, T, V> filter(final Predicate<K> filter);

    /**
     * @return Smallest temporal value
     */
    T minValue();

    /**
     * @return Largest temporal value
     */
    T maxValue();

    /**
     * @return The number of distinct temporal values
     */
    int length();

    /**
     * @param key Key value
     * @return {@link TimeSeries} for a given key
     */
    TimeSeries<T, V> get(final K key);

    /**
     * Perform calculations using only {@link NonTemporalCalculator}s
     *
     * @param nonTemporalCalculators Calculations to perform
     */
    default void calculateNonTemporal(final Collection<NonTemporalCalculator<K, ? super V, ?>> nonTemporalCalculators) {
        calculate(nonTemporalCalculators, Collections.emptyList());
    }

    /**
     * Perform calculations using only {@link TemporalCalculator}s
     *
     * @param temporalCalculators Calculations to perform
     */
    default void calculateTemporal(final Collection<TemporalCalculator<K, T, ? super V, ?>> temporalCalculators) {
        calculate(Collections.emptyList(), temporalCalculators);
    }

    /**
     * Perform calculations using a mix of {@link NonTemporalCalculator} & {@link TemporalCalculator} calculations
     *
     * @param nonTemporalCalculators Non-temporal calculations to perform
     * @param temporalCalculators    Temporal calculations to perform
     */
    void calculate(final Collection<NonTemporalCalculator<K, ? super V, ?>> nonTemporalCalculators, final Collection<TemporalCalculator<K, T, ? super V, ?>> temporalCalculators);
}