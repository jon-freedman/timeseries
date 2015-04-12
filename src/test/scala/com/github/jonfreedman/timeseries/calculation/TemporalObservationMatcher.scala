package com.github.jonfreedman.timeseries.calculation

import com.github.jonfreedman.timeseries.calculation.TemporalCalculator.Observation
import org.hamcrest.{Description, Matcher, TypeSafeMatcher}

/**
 * @author jon
 */
class TemporalObservationMatcher[CT >: T, T <: Comparable[CT], R] private(timeValueMatcher: Matcher[_ <: T], valueMatcher: Matcher[_ <: R]) extends TypeSafeMatcher[TemporalCalculator.Observation[T, R]] {
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
  def observation[CT >: T, T <: Comparable[CT], R](timeValueMatcher: Matcher[_ <: T], valueMatcher: Matcher[_ <: R]): TemporalObservationMatcher[CT, T, R] =
    new TemporalObservationMatcher[CT, T, R](timeValueMatcher, valueMatcher)
}
