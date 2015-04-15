package com.github.jonfreedman.timeseries

import java.lang
import java.time.LocalDate
import java.util.concurrent.Executors

import com.github.jonfreedman.timeseries.ArrayTimeSeriesCollection.Builder
import com.github.jonfreedman.timeseries.interpolator.ZeroValueInterpolator
import com.github.jonfreedman.timeseries.localdate.{LocalDateTraverser, WeekdayLocalDateTraverser}
import org.junit.Test

/**
 * @author jon
 */
class TestArrayTimeSeriesCollectionBuilder {
  @Test(expected = classOf[IllegalStateException]) def buildingAnEmptyTimeSeriesCollectionThrowsAnException() {
    new Builder[String, LocalDate, Double].build(new ZeroValueInterpolator[Double](0d), LocalDateTraverser.factory())
  }

  @Test(expected = classOf[NullPointerException]) def cannotAddNullKey() {
    new Builder[String, LocalDate, Double].addValue(null, LocalDate.of(2015, 4, 5), 1)
  }

  @Test(expected = classOf[NullPointerException]) def cannotAddNullTimeValue() {
    new Builder[String, LocalDate, Double].addValue("foo", null, 1)
  }

  @Test(expected = classOf[NullPointerException]) def cannotAddNullValue() {
    new Builder[String, LocalDate, lang.Double].addValue("foo", LocalDate.of(2015, 4, 5), null)
  }

  @Test(expected = classOf[IllegalArgumentException]) def cannotInsertSingleInvalidValue() {
    new Builder[String, LocalDate, Double]
      .addValue("foo", LocalDate.of(2015, 4, 5), 1)
      .build(new ZeroValueInterpolator[Double](0d), WeekdayLocalDateTraverser.factory())
  }

  @Test(expected = classOf[IllegalArgumentException]) def cannotStartWithInvalidValue() {
    new Builder[String, LocalDate, Double]
      .addValue("foo", LocalDate.of(2015, 4, 5), 0)
      .addValue("foo", LocalDate.of(2015, 4, 6), 1)
      .build(new ZeroValueInterpolator[Double](0d), WeekdayLocalDateTraverser.factory())
  }

  @Test(expected = classOf[IllegalArgumentException]) def cannotEndWithInvalidValue() {
    new Builder[String, LocalDate, Double]
      .addValue("foo", LocalDate.of(2015, 4, 3), 1)
      .addValue("foo", LocalDate.of(2015, 4, 4), 0)
      .build(new ZeroValueInterpolator[Double](0d), WeekdayLocalDateTraverser.factory())
  }

  @Test(expected = classOf[IllegalArgumentException]) def cannotInsertInvalidValues() {
    new Builder[String, LocalDate, Double]
      .addValue("foo", LocalDate.of(2015, 4, 2), 1)
      .addValue("foo", LocalDate.of(2015, 4, 3), 1)
      .addValue("foo", LocalDate.of(2015, 4, 4), 0)
      .addValue("foo", LocalDate.of(2015, 4, 5), 0)
      .addValue("foo", LocalDate.of(2015, 4, 6), 1)
      .build(new ZeroValueInterpolator[Double](0d), WeekdayLocalDateTraverser.factory())
  }

  @Test(expected = classOf[UnsupportedOperationException]) def cannotAddMultipleObservationsForKeyAndTimeValueWithNoArgConstructor() {
    new Builder[String, LocalDate, Double]
      .addValue("foo", LocalDate.of(2015, 4, 2), 1)
      .addValue("foo", LocalDate.of(2015, 4, 2), 1)
  }
}
