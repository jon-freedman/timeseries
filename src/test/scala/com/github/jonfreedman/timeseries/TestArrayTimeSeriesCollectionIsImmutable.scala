package com.github.jonfreedman.timeseries

import java.time.LocalDate

import com.github.jonfreedman.timeseries.ArrayTimeSeriesCollection.Builder
import com.github.jonfreedman.timeseries.interpolator.ZeroValueInterpolator
import com.github.jonfreedman.timeseries.localdate.LocalDateTraverser
import org.junit.Test

/**
 * @author jon
 */
class TestArrayTimeSeriesCollectionIsImmutable {
  private val key: String = "foo"
  private val tsc = new Builder[String, LocalDate, Double]
    .addValue(key, LocalDate.of(2015, 4, 6), 1d)
    .build(new ZeroValueInterpolator[Double](0d), LocalDateTraverser.factory())

  @Test(expected = classOf[UnsupportedOperationException]) def throwsWhenModifyingEntry() {
    val entry = tsc.iterator().next()
    entry.setValue(null)
  }

  @Test(expected = classOf[UnsupportedOperationException]) def throwsWhenModifyingTimeSeriesEntry() {
    val ts = tsc.get(key)
    val entry = ts.timeValueIterator().next()
    entry.setValue(2d)
  }
}
