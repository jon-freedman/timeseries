package com.github.jonfreedman.timeseries.steps.helpers

import java.lang
import java.time.LocalDate

import com.github.jonfreedman.timeseries.ArrayTimeSeriesCollection
import com.github.jonfreedman.timeseries.ArrayTimeSeriesCollection.Builder
import com.github.jonfreedman.timeseries.interpolator.FlatFillInterpolator.Direction
import com.github.jonfreedman.timeseries.interpolator.{FlatFillInterpolator, ValueInterpolator}
import com.github.jonfreedman.timeseries.localdate.LocalDateTraverser
import com.github.jonfreedman.timeseries.steps.helpers.ArrayTimeSeriesCollectionHelper.LocalDateCollectionBuilder
import cucumber.runtime.java.guice.ScenarioScoped

/**
 * @author jon
 */
@ScenarioScoped class ArrayTimeSeriesCollectionHelper {
  var builder: LocalDateCollectionBuilder = _

  lazy val collection: ArrayTimeSeriesCollection[String, LocalDate, lang.Double] = builder.build()
}

object ArrayTimeSeriesCollectionHelper {

  class LocalDateCollectionBuilder(first: LocalDate, last: LocalDate,
                                   values: Map[String, Map[LocalDate, lang.Double]] = Map.empty,
                                   interpolator: ValueInterpolator[lang.Double] = new FlatFillInterpolator[lang.Double](Direction.forward)) {
    def addValue(key: String, date: LocalDate, value: Double): LocalDateCollectionBuilder =
      new LocalDateCollectionBuilder(first, last, values.updated(key, values.getOrElse(key, Map.empty).updated(date, Double.box(value))), interpolator)

    def withInterpolator(interpolator: ValueInterpolator[lang.Double]): LocalDateCollectionBuilder =
      new LocalDateCollectionBuilder(first, last, values, interpolator)

    private[ArrayTimeSeriesCollectionHelper] def build(): ArrayTimeSeriesCollection[String, LocalDate, lang.Double] = {
      val builder = new Builder[String, LocalDate, lang.Double]
      for {
        (key, vs) <- values
        (date, value) <- vs
      } {
        builder.addValue(key, date, value)
      }
      builder.build(interpolator, LocalDateTraverser.factory())
    }
  }

}