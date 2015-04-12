package com.github.jonfreedman.timeseries

import java.time.LocalDate

import com.github.jonfreedman.timeseries.ArrayTimeSeriesCollection.Builder
import com.github.jonfreedman.timeseries.interpolator.ZeroValueInterpolator
import com.github.jonfreedman.timeseries.localdate.LocalDateTraverser
import org.junit.Assert._
import org.junit.Test

/**
 * @author jon
 */
class TestArrayTimeSeriesCollectionIsImmutable {
  private val key = "foo"
  private val timeValue = LocalDate.of(2015, 4, 6)
  private val tsc = new Builder[String, LocalDate, Double]
    .addValue(key, timeValue, 1d)
    .build(new ZeroValueInterpolator[Double](0d), LocalDateTraverser.factory())

  @Test(expected = classOf[UnsupportedOperationException]) def throwsWhenModifyingEntry() {
    val iterator = tsc.iterator()
    assertTrue(iterator.hasNext)
    val entry = iterator.next()
    assertEquals(key, entry.getKey)
    entry.setValue(null)
  }

  @Test(expected = classOf[UnsupportedOperationException]) def throwsWhenModifyingTimeSeriesEntry() {
    val ts = tsc.get(key)
    val iterator = ts.timeValueIterator()
    assertTrue(iterator.hasNext)
    val entry = iterator.next()
    assertEquals(timeValue, entry.getKey)
    entry.setValue(2d)
  }
}
