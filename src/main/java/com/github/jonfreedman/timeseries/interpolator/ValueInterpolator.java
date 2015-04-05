package com.github.jonfreedman.timeseries.interpolator;

/**
 * Used to supply missing values in a spare TimeSeries
 *
 * @author jon
 */
public interface ValueInterpolator<V> {
    /**
     * Interpolate an y value for a given x given optional previous and next (x,y) values
     *
     * @param x     x value used to interpolate a y
     * @param prevX Previous x value
     * @param prevY y value for prevX
     * @param nextX Next x value
     * @param nextY y value for nextX
     * @return Interpolated y value for x
     */
    V getY(final int x, final int prevX, final V prevY, final int nextX, final V nextY);
}
