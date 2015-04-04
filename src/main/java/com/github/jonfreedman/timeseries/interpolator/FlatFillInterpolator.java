package com.github.jonfreedman.timeseries.interpolator;

/**
 * @author jon
 */
public final class FlatFillInterpolator<V> implements ValueInterpolator<V> {
    private final Direction direction;

    public FlatFillInterpolator(final Direction direction) {
        this.direction = direction;
    }

    @Override
    public V getY(final int x, final int prevX, final V prevY, final int nextX, final V nextY) {
        if (prevY == null) {
            return nextY;
        }
        if (nextY == null) {
            return prevY;
        }
        switch (direction) {
            case forward:
                return prevY;
            case backward:
                return nextY;
            default:
                throw new IllegalStateException("Cannot interpolate a value for " + x);
        }
    }

    public enum Direction {
        forward, backward
    }
}
