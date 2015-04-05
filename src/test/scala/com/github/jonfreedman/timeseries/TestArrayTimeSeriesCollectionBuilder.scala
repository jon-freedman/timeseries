package com.github.jonfreedman.timeseries

import java.lang
import java.time.LocalDate

import com.github.jonfreedman.timeseries.ArrayTimeSeriesCollection.Builder
import com.github.jonfreedman.timeseries.interpolator.ZeroValueInterpolator
import com.github.jonfreedman.timeseries.localdate.LocalDateTraverser
import org.junit.Test

/**
 * @author jon
 */
class TestArrayTimeSeriesCollectionBuilder {
  @Test(expected = classOf[IllegalStateException]) def buildingAnEmptyTimeSeriesCollectionThrowsAnException() {
    new Builder[String, LocalDate, Double].build(new ZeroValueInterpolator[Double](0d), LocalDateTraverser.factory())
  }

  @Test(expected = classOf[IllegalArgumentException]) def cannotAddNullKey() {
    new Builder[String, LocalDate, Double].addValue(null, LocalDate.of(2015, 4, 5), 1)
  }

  @Test(expected = classOf[IllegalArgumentException]) def cannotAddNullTimeValue() {
    new Builder[String, LocalDate, Double].addValue("foo", null, 1)
  }

  @Test(expected = classOf[IllegalArgumentException]) def cannotAddNullValue() {
    new Builder[String, LocalDate, lang.Double].addValue("foo", LocalDate.of(2015, 4, 5), null)
  }
}
