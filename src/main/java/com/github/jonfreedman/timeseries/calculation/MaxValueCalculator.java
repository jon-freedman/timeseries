package com.github.jonfreedman.timeseries.calculation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author jon
 */
public class MaxValueCalculator<K extends Comparable<K>, T extends Comparable<? super T>, V extends Comparable<? super V>> implements TemporalCalculator<K, T, V, TemporalCalculator.Observation<T, V>> {
    private final Map<K, TemporalCalculator.Observation<T, V>> result = new HashMap<>();

    @Override
    public void observation(final K key, final Supplier<T> timeValue, final V value) {
        final Observation<T, V> curr = new Observation<>(timeValue, value);
        final Observation<T, V> prev = result.getOrDefault(key, curr);
        result.put(key, curr.getValue().compareTo(prev.getValue()) > 0 ? curr : prev);
    }

    @Override
    public Map<K, TemporalCalculator.Observation<T, V>> results() {
        return result;
    }
}
