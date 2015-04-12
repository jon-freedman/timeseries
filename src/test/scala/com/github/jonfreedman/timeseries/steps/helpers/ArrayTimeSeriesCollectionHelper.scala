package com.github.jonfreedman.timeseries.steps.helpers

import java.time.LocalDate
import java.{lang, util}

import com.github.jonfreedman.timeseries.ArrayTimeSeriesCollection.Builder
import com.github.jonfreedman.timeseries.interpolator.FlatFillInterpolator.Direction
import com.github.jonfreedman.timeseries.interpolator.{FlatFillInterpolator, ValueInterpolator}
import com.github.jonfreedman.timeseries.localdate.LocalDateTraverser
import com.github.jonfreedman.timeseries.steps.helpers.ArrayTimeSeriesCollectionHelper.LocalDateCollectionBuilder
import com.github.jonfreedman.timeseries.{ArrayTimeSeriesCollection, Traverser}
import cucumber.runtime.java.guice.ScenarioScoped

/**
 * @author jon
 */
@ScenarioScoped class ArrayTimeSeriesCollectionHelper {
  var builder: LocalDateCollectionBuilder = _

  lazy val collection: ArrayTimeSeriesCollection[String, LocalDate, lang.Double] = builder.build()
}

object ArrayTimeSeriesCollectionHelper {

  class LocalDateCollectionBuilder(values: Map[String, Map[LocalDate, lang.Double]],
                                   interpolator: ValueInterpolator[lang.Double],
                                   traverser: util.function.Function[LocalDate, Traverser[LocalDate]]) {
    def this() = this(Map.empty, new FlatFillInterpolator[lang.Double](Direction.forward), LocalDateTraverser.factory())

    def addValue(key: String, date: LocalDate, value: Double): LocalDateCollectionBuilder =
      new LocalDateCollectionBuilder(values.updated(key, values.getOrElse(key, Map.empty).updated(date, Double.box(value))), interpolator, traverser)

    def withInterpolator(interpolator: ValueInterpolator[lang.Double]): LocalDateCollectionBuilder =
      new LocalDateCollectionBuilder(values, interpolator, traverser)

    def withTraverser(traverser: util.function.Function[LocalDate, Traverser[LocalDate]]): LocalDateCollectionBuilder =
      new LocalDateCollectionBuilder(values, interpolator, traverser)

    def build(): ArrayTimeSeriesCollection[String, LocalDate, lang.Double] = {
      val builder = new Builder[String, LocalDate, lang.Double]
      for {
        (key, vs) <- values
        (date, value) <- vs
      } {
        builder.addValue(key, date, value)
      }
      builder.build(interpolator, traverser)
    }
  }

}