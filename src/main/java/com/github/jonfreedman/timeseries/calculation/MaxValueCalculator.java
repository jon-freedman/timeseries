package com.github.jonfreedman.timeseries.calculation;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author jon
 */
public class MaxValueCalculator<K extends Comparable<K>, T extends Comparable<? super T>> implements TemporalCalculator<K, T, Number, TemporalCalculator.Observation<T, BigDecimal>> {
    private final Map<K, TemporalCalculator.Observation<T, BigDecimal>> result = new HashMap<>();

    @Override
    public void observation(final K key, final Supplier<T> timeValue, final Number value) {
        final Observation<T, BigDecimal> curr = new Observation<>(timeValue, BigDecimal.valueOf(value.doubleValue()));
        final Observation<T, BigDecimal> prev = result.getOrDefault(key, curr);
        result.put(key, curr.getValue().compareTo(prev.getValue()) > 0 ? curr : prev);
    }

    @Override
    public Map<K, TemporalCalculator.Observation<T, BigDecimal>> results() {
        return result;
    }
}
