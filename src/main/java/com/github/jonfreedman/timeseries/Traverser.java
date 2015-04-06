package com.github.jonfreedman.timeseries;

/**
 * Used to traverse a potentially infinite sequence of ordered values, caller is responsible for terminating the traversal
 *
 * Required in order to create a discrete sequence of continuous time values
 *
 * @author jon
 */
public interface Traverser<T extends Comparable<? super T>> {
    /**
     * @param initialValue Potential initial value
     * @return True if initial value is a valid start of a sequence
     */
    boolean canStartFrom(final T initialValue);

    /**
     * Traverses to the logical next value based on the natural ordering of T
     *
     * @return Next value after traversing
     */
    T next();

    /**
     * Allows traversal of sparsely populated sequences, traverses to the nth next value based on the natural ordering of T
     * <p>
     * Calling #skip(0) is equivalent to #next()
     *
     * @param n Values to skip, must be >= 0
     * @return Next value after skipping n values and traversing
     */
    T skip(final int n);
}
