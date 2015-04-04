package com.github.jonfreedman.timeseries.interpolator;

/**
 * @author jon
 */
public abstract class AbstractInterpolator<V> implements ValueInterpolator<V> {
    private Interpolation<V> func;

    protected AbstractInterpolator(Interpolation<V> func) {
        this.func = func;
    }

    @Override
    public V getY(final int x, final int prevX, final V prevY, final int nextX, final V nextY) {
        return func.interp(x, prevX, prevY, nextX, nextY);
    }

    public interface Interpolation<V> {
        public V interp(final int x, final int x1, final V y1, final int x2, final V y2);
    }
}
