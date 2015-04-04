package com.github.jonfreedman.timeseries.interpolator;

/**
 * @author jon
 */
public final class ZeroValueInterpolator<V> implements ValueInterpolator<V> {
    private final V zero;

    public ZeroValueInterpolator(V zero) {
        this.zero = zero;
    }

    @Override
    public V getY(final int x, final int prevX, final V prevY, final int nextX, final V nextY) {
        return zero;
    }
}
