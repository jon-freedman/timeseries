package com.github.jonfreedman.timeseries.matchers

import com.github.jonfreedman.timeseries.calculation.TemporalCalculator
import com.github.jonfreedman.timeseries.calculation.TemporalCalculator.Observation
import org.hamcrest.{Description, Matcher, TypeSafeMatcher}

/**
 * @author jon
 */
class TemporalObservationMatcher[T, R] private(timeValueMatcher: Matcher[_ <: T], valueMatcher: Matcher[_ <: R])(implicit ev: T <:< Comparable[_ >: T]) extends TypeSafeMatcher[TemporalCalculator.Observation[T, R]] {
  override def matchesSafely(item: Observation[T, R]): Boolean = timeValueMatcher.matches(item.getTimeValue) && valueMatcher.matches(item.getValue)

  override def describeTo(description: Description): Unit = {
    description.appendText("Observation[timeValue: ")
    timeValueMatcher.describeTo(description)
    description.appendText(" value: ")
    valueMatcher.describeTo(description)
    description.appendText("]")
  }
}

object TemporalObservationMatcher {
  def observation[T, R](timeValueMatcher: Matcher[_ <: T], valueMatcher: Matcher[_ <: R])(implicit ev: T <:< Comparable[_ >: T]): TemporalObservationMatcher[T, R] =
    new TemporalObservationMatcher[T, R](timeValueMatcher, valueMatcher)
}
