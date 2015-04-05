package com.github.jonfreedman.timeseries;

import com.github.jonfreedman.timeseries.interpolator.ValueInterpolator;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

/**
 * @author jon
 */
public final class ArrayTimeSeriesCollection<K extends Comparable<K>, T extends Comparable<? super T>, V> implements TimeSeriesCollection<K, T, V> {
    private final ConcurrentMap<Integer, T> timeLookup = new ConcurrentHashMap<>();
    private final Function<T, Traverser<T>> traverserFactory;
    private final Map<K, V[]> values;
    private final T initialTimeValue;
    private final int maxIndex;

    public ArrayTimeSeriesCollection(final T initialTimeValue, final Function<T, Traverser<T>> traverserFactory,
                                     final Map<K, V[]> values) {
        this.initialTimeValue = initialTimeValue;
        this.traverserFactory = traverserFactory;
        this.values = values;

        int max = 0;
        for (final V[] vs : values.values()) {
            max = Math.max(max, vs.length - 1);
        }
        maxIndex = max;
    }

    @Override
    public SortedSet<K> keySet() {
        return new TreeSet<>(values.keySet());
    }

    @Override
    public T minValue() {
        return initialTimeValue;
    }

    @Override
    public T maxValue() {
        return getWithPutIfAbsent(timeLookup, maxIndex, (v) -> {
            final Traverser<T> traverser = traverserFactory.apply(initialTimeValue);
            return traverser.skip(maxIndex - 1);
        });
    }

    @Override
    public TimeSeries<T, V> get(K key) {
        return null;
    }

    private static <K, V> V getWithPutIfAbsent(final ConcurrentMap<K, V> map, final K key, final Function<Void, V> func) {
        V val = map.get(key);
        if (val == null) {
            final V constructed = func.apply(null);
            val = map.putIfAbsent(key, constructed);
            if (val == null) {
                val = constructed;
            }
        }
        return val;
    }

    /**
     * As TimeSeriesCollection instances are immutable use a Builder to load data and construct an instance
     */
    public static final class Builder<K extends Comparable<K>, T extends Comparable<? super T>, V> {
        private final ConcurrentMap<K, ConcurrentMap<T, V>> state = new ConcurrentHashMap<>();

        public Builder<K, T, V> addValue(K key, T x, V y) {
            final ConcurrentMap<T, V> values = getWithPutIfAbsent(state, key, (v) -> new ConcurrentHashMap<>());
            values.put(x, y);
            return this;
        }

        public ArrayTimeSeriesCollection<K, T, V> build(final ValueInterpolator<V> interpolator,
                                                        final Function<T, Traverser<T>> traverserFactory) {
            // build set of all time values present across all keys
            final SortedSet<T> timeValues = new TreeSet<>();
            for (final Map<T, V> vs : state.values()) {
                timeValues.addAll(vs.keySet());
            }
            if (timeValues.isEmpty()) {
                throw new IllegalStateException("Cannot construct an empty TimeSeriesCollection");
            }
            final T minTimeValue = timeValues.first();
            final T maxTimeValue = timeValues.last();

            // prepare integer indexed data for interpolation
            final Traverser<T> traverser = traverserFactory.apply(minTimeValue);
            T timeValue = minTimeValue;
            int x = 0;
            final Map<K, SortedMap<Integer, V>> values = new TreeMap<>();
            for (final K key : state.keySet()) {
                values.put(key, new TreeMap<>());
            }
            while (timeValue.compareTo(maxTimeValue) <= 0) {
                for (final Map.Entry<K, ConcurrentMap<T, V>> entry : state.entrySet()) {
                    final V val = entry.getValue().get(timeValue);
                    if (val != null) {
                        values.get(entry.getKey()).put(x, val);
                    }
                }
                timeValues.add(timeValue);
                ++x;
                timeValue = traverser.next();
            }

            // fill in any missing values
            V sampleValue = null;
            for (int i = 0; i < timeValues.size(); ++i) {
                for (final Map.Entry<K, SortedMap<Integer, V>> entry : values.entrySet()) {
                    SortedMap<Integer, V> ts = entry.getValue();
                    if (ts.get(i) == null) {
                        final SortedMap<Integer, V> headMap = ts.headMap(i);
                        final SortedSet<Integer> tailKeys = new TreeSet<>(ts.tailMap(i).keySet());
                        tailKeys.remove(i);

                        if (!headMap.isEmpty() && !tailKeys.isEmpty()) {
                            final int prevX = headMap.lastKey();
                            final V prevY = ts.get(prevX);
                            final int nextX = tailKeys.first();
                            final V nextY = ts.get(nextX);
                            ts.put(i, interpolator.getY(i, prevX, prevY, nextX, nextY));
                        } else if (!headMap.isEmpty()) {
                            final int prevX = headMap.lastKey();
                            final V prevY = ts.get(prevX);
                            ts.put(i, interpolator.getY(i, prevX, prevY, 0, null));
                        } else if (!tailKeys.isEmpty()) {
                            final int nextX = tailKeys.first();
                            final V nextY = ts.get(nextX);
                            ts.put(i, interpolator.getY(i, 0, null, nextX, nextY));
                        }
                    } else if (sampleValue == null) {
                        sampleValue = ts.get(i);
                    }
                }
            }

            // convert to arrays and build
            final Map<K, V[]> arrayValues = new HashMap<>();
            for (final Map.Entry<K, SortedMap<Integer, V>> entry : values.entrySet()) {
                //noinspection unchecked
                final V[] array = (V[]) Array.newInstance(sampleValue.getClass(), timeValues.size());
                arrayValues.put(entry.getKey(), entry.getValue().values().toArray(array));
            }
            return new ArrayTimeSeriesCollection<>(minTimeValue, traverserFactory, arrayValues);
        }
    }
}
