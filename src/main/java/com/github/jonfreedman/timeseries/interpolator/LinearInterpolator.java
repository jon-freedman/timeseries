package com.github.jonfreedman.timeseries.interpolator;

/**
 * @author jon
 */
public final class LinearInterpolator extends AbstractInterpolator<Double> {
    public LinearInterpolator() {
        super((x, x1, y1, x2, y2) -> {
            if (y1 == null || y2 == null) {
                return null;
            } else {
                return y1 + ((y2 - y1) * ((double) (x - x1) / (double) (x2 - x1)));
            }
        });
    }
}