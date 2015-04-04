package com.github.jonfreedman.timeseries.steps.helpers

import java.time.LocalDate
import java.{lang, util}

import com.github.jonfreedman.timeseries.ArrayTimeSeriesCollection.Builder
import com.github.jonfreedman.timeseries.interpolator.FlatFillInterpolator
import com.github.jonfreedman.timeseries.interpolator.FlatFillInterpolator.Direction
import com.github.jonfreedman.timeseries.localdate.LocalDateTraverser
import com.github.jonfreedman.timeseries.steps.helpers.ArrayTimeSeriesCollectionHelper.LocalDateCollectionBuilder
import com.github.jonfreedman.timeseries.{ArrayTimeSeriesCollection, Traverser}
import cucumber.runtime.java.guice.ScenarioScoped

import scala.collection.mutable

/**
 * @author jon
 */
@ScenarioScoped class ArrayTimeSeriesCollectionHelper {
  var builder: LocalDateCollectionBuilder = _

  lazy val collection: ArrayTimeSeriesCollection[String, LocalDate, lang.Double] = builder.build()
}

object ArrayTimeSeriesCollectionHelper {

  class LocalDateCollectionBuilder(first: LocalDate, last: LocalDate) {
    private val values: mutable.Map[String, mutable.Map[LocalDate, lang.Double]] = new mutable.HashMap

    def addValue(key: String, date: LocalDate, value: Double): LocalDateCollectionBuilder = {
      values.getOrElseUpdate(key, new mutable.HashMap[LocalDate, lang.Double]).put(date, value)
      this
    }

    private[ArrayTimeSeriesCollectionHelper] def build(): ArrayTimeSeriesCollection[String, LocalDate, lang.Double] = {
      val builder = new Builder[String, LocalDate, lang.Double]
      for {
        (key, vs) <- values
        (date, value) <- vs
      } {
        builder.addValue(key, date, value)
      }
      builder.build(new FlatFillInterpolator[lang.Double](Direction.forward), new util.function.Function[LocalDate, Traverser[LocalDate]] {
        override def apply(d: LocalDate): Traverser[LocalDate] = new LocalDateTraverser(d)
      })
    }
  }

}