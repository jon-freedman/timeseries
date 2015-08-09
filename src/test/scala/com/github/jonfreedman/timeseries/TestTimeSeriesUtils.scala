package com.github.jonfreedman.timeseries

import java.lang
import java.time.LocalDate

import com.github.jonfreedman.timeseries.ArrayTimeSeriesCollection.Builder
import com.github.jonfreedman.timeseries.interpolator.ZeroValueInterpolator
import com.github.jonfreedman.timeseries.localdate.LocalDateTraverser
import com.github.jonfreedman.timeseries.matchers.EntryMatcher._
import org.hamcrest.Matchers._
import org.junit.Assert._
import org.junit.Test

/**
 * @author jon
 */
class TestTimeSeriesUtils {
  private val key = "foo"
  private val timeValue1 = LocalDate.of(2015, 8, 10)
  private val timeValue2 = LocalDate.of(2015, 8, 11)
  private val tsc = new Builder[String, LocalDate, lang.Double]
    .addValue(key, timeValue1, 1d)
    .addValue(key, timeValue2, 2d)
    .build(new ZeroValueInterpolator[lang.Double](0d), LocalDateTraverser.factory())

  @Test def buildTimeValueIterable() {
    assertThat(TimeSeries.Util.asTimeValueIterable(tsc.get(key)), contains(entry(equalTo(timeValue1), closeTo(1d, 1e-8)), entry(equalTo(timeValue2), closeTo(2d, 1e-8))))
  }

  @Test def buildOrdinalIterable() {
    assertThat(TimeSeries.Util.asOrdinalIterable(tsc.get(key)), contains(closeTo(1d, 1e-8), closeTo(2d, 1e-8)))
  }
}
