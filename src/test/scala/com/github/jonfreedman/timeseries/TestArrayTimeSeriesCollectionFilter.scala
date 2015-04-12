package com.github.jonfreedman.timeseries

import java.time.LocalDate
import java.util.function.Predicate

import com.github.jonfreedman.timeseries.ArrayTimeSeriesCollection.Builder
import com.github.jonfreedman.timeseries.interpolator.ZeroValueInterpolator
import com.github.jonfreedman.timeseries.localdate.LocalDateTraverser
import org.hamcrest.Matchers._
import org.junit.Assert._
import org.junit.Test

/**
 * @author jon
 */
class TestArrayTimeSeriesCollectionFilter {
  private val key1 = "foo"
  private val key2 = "bar"
  private val timeValue = LocalDate.of(2015, 4, 6)
  private val tsc = new Builder[String, LocalDate, Double]
    .addValue(key1, timeValue, 1d)
    .addValue(key2, timeValue, 2d)
    .build(new ZeroValueInterpolator[Double](0d), LocalDateTraverser.factory())

  @Test def filteringResultingInSameKeySetIsIdentityFunction() {
    assertThat(tsc, equalTo(tsc.filter(new Predicate[String] {
      override def test(t: String): Boolean = true
    })))
  }

  @Test(expected = classOf[IllegalArgumentException]) def filteredCollectionCannotBeEmpty() {
    tsc.filter(Predicate.isEqual("baz"))
  }
}
