package com.github.jonfreedman.timeseries.calculation.number;

import com.github.jonfreedman.timeseries.calculation.NonTemporalCalculator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Calculates the sum of all numerical observations in a {@link com.github.jonfreedman.timeseries.TimeSeries} as a
 * {@link BigDecimal}
 *
 * @author jon
 */
public class SumCalculator<K extends Comparable<K>> implements NonTemporalCalculator<K, Number, BigDecimal> {
    private final Map<K, BigDecimal> result = new HashMap<>();

    @Override
    public void observation(K key, Number value) {
        final BigDecimal prev = result.getOrDefault(key, BigDecimal.ZERO);
        result.put(key, prev.add(BigDecimal.valueOf(value.doubleValue())));
    }

    @Override
    public Map<K, BigDecimal> results() {
        return result;
    }
}
